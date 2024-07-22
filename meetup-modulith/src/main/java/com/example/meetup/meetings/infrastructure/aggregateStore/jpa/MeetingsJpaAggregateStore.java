package com.example.meetup.meetings.infrastructure.aggregateStore.jpa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.meetup.meetings.base.domain.Aggregate;
import com.example.meetup.meetings.base.domain.IAggregateStore;
import com.example.meetup.meetings.base.domain.IDomainEvent;
import com.example.meetup.meetings.base.domain.TypedIdValueBase;
import com.example.meetup.meetings.base.infrastructure.ISubscriptionsManager;
import com.example.meetup.meetings.domain.meeting.Meeting;
import com.example.meetup.meetings.domain.meetingComment.MeetingComment;
import com.example.meetup.meetings.domain.meetingCommentingConfiguration.MeetingCommentingConfiguration;
import com.example.meetup.meetings.domain.meetingGroup.MeetingGroup;
import com.example.meetup.meetings.domain.meetingGroupProposal.MeetingGroupProposal;
import com.example.meetup.meetings.domain.meetingMemberCommentLike.MeetingMemberCommentLike;
import com.example.meetup.meetings.domain.member.Member;
import com.example.meetup.meetings.domain.memberSubscriptions.MemberSubscription;
import com.example.meetup.meetings.infrastructure.aggregateStore.CuncurrentAccessException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


@Component
public class MeetingsJpaAggregateStore implements IAggregateStore {

	private ISubscriptionsManager subscriptionsManager;
	
	private ApplicationEventPublisher applicationEventPublisher;
	
	private Map<Class<? extends Aggregate>, Supplier<? extends Aggregate>> aggregateSuppliers;
	
	private List<IDomainEvent> domainEvents;
	
	private MeetingsEventStreamRepository streamRepository;
	
	private ObjectMapper objectMapper;

	public MeetingsJpaAggregateStore(
			ISubscriptionsManager subscriptionsManager, 
			ApplicationEventPublisher applicationEventPublisher,
			MeetingsEventStreamRepository streamRepository) {
		
		this.subscriptionsManager = subscriptionsManager;
		this.applicationEventPublisher = applicationEventPublisher;
		this.streamRepository = streamRepository;
		
		objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
				
		domainEvents = new ArrayList<>();

		aggregateSuppliers = new HashMap<>();
		
		aggregateSuppliers.put(MeetingGroupProposal.class, 
			() -> new MeetingGroupProposal());
			
		aggregateSuppliers.put(MeetingCommentingConfiguration.class, 
			() -> new MeetingCommentingConfiguration());
			
		aggregateSuppliers.put(MeetingComment.class, 
			() -> new MeetingComment());
			
		aggregateSuppliers.put(MeetingGroup.class, 
			() -> new MeetingGroup());
			
		aggregateSuppliers.put(MeetingMemberCommentLike.class, 
			() -> new MeetingMemberCommentLike());
			
		aggregateSuppliers.put(Meeting.class, 
			() -> new Meeting());
			
		aggregateSuppliers.put(Member.class, 
			() -> new Member());
			
		aggregateSuppliers.put(MemberSubscription.class, 
			() -> new MemberSubscription());
			
	}

	@Transactional
	@Override
	public void save(Aggregate... aggregates) {

		checkVersionForCuncurrentAccess(aggregates);
		
		updateEventStore(aggregates);

		updateViews(aggregates);
		
		notifyEventListeners(aggregates);
	}
	
	private void checkVersionForCuncurrentAccess(Aggregate... aggregates) {
		for (var aggregateToSave : aggregates) {
			var streamId = aggregateToSave.getStreamId();
			
			if (streamRepository.findByStreamId(streamId) != null) {
				var streamVersion = streamRepository.findByStreamId(streamId).getVersion();
				if (streamVersion != aggregateToSave.getVersion() - aggregateToSave.getDomainEvents().size())
					throw new CuncurrentAccessException();
			}
		}
	}

	private void updateEventStore(Aggregate... aggregates) {
		for (var aggregateToSave : aggregates) {

			var streamId = aggregateToSave.getStreamId();

			if (streamRepository.findByStreamId(streamId) == null)
				streamRepository.save(
						new MeetingsEventStream(
								streamId,
								0,
								new ArrayList<>()));

			var stream = streamRepository.findByStreamId(streamId);

			for (var event : aggregateToSave.getDomainEvents()) {
				try {
					stream.getEvents().add(
							new MeetingsEvent(
									event.getId(),
									event.getClass().getName(),
									event.occuredOn(),
									objectMapper.writeValueAsString(event)));
				} catch (JsonProcessingException e) {
					throw new RuntimeException(e.getMessage());
				}

				// for testing
				domainEvents.add(event);
			}
			
			stream.setVersion(aggregateToSave.getDomainEvents().size() + stream.getVersion());		
		}
	}
	
//	@Async
	private void updateViews(Aggregate... aggregates) {
		for (var aggregateToSave : aggregates) {
			for (var event : aggregateToSave.getDomainEvents()) {
				subscriptionsManager.addEvent(event);
			}
		}
	}
	
	private void notifyEventListeners(Aggregate... aggregates) {
		for (var aggregateToSave : aggregates) {
			for (var event : aggregateToSave.getDomainEvents()) {
				applicationEventPublisher.publishEvent(event);
			}
		}
	}

	@Override
	public <T extends Aggregate, V> T load(TypedIdValueBase<V> aggregateId, Class<? extends Aggregate> aggregateType) {

		var streamId = "Meetings-" + aggregateType.getSimpleName() + "-" + aggregateId.getValue();

		if (streamRepository.findByStreamId(streamId) == null)
			return null;
		
		var events = streamRepository.findByStreamId(streamId)
				.getEvents().stream()
				.map(jpaEvent -> {
					try {
						return objectMapper.readValue(
								jpaEvent.getData()
								, Class.forName(jpaEvent.getEventName()));
					} catch (JsonProcessingException | ClassNotFoundException e) {
						throw new RuntimeException(e);
					}
				})
				.map(event -> (IDomainEvent) event).toList();
				
		Aggregate aggregate = aggregateSuppliers.get(aggregateType).get();

		aggregate.load(events);

		return (T) aggregate;
	}
	
	@Override
	public List<IDomainEvent> getDomainEvents() {
		return new ArrayList<>(domainEvents);
	}

	@Override
	public void clearDomainEvents() {
		domainEvents.clear();
	}
}
