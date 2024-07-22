package com.example.meetup.administration.infrastructure.aggregateStore.jpa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.meetup.administration.base.domain.Aggregate;
import com.example.meetup.administration.base.domain.IAggregateStore;
import com.example.meetup.administration.base.domain.IDomainEvent;
import com.example.meetup.administration.base.domain.TypedIdValueBase;
import com.example.meetup.administration.base.infrastructure.ISubscriptionsManager;
import com.example.meetup.administration.infrastructure.aggregateStore.CuncurrentAccessException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


@Component
public class AdministrationJpaAggregateStore implements IAggregateStore {

private ISubscriptionsManager subscriptionsManager;
	
	private ApplicationEventPublisher applicationEventPublisher;
	
	private Map<Class<? extends Aggregate>, Supplier<? extends Aggregate>> aggregateSuppliers;
	
	private List<IDomainEvent> domainEvents;
	
	private AdministrationEventStreamRepository streamRepository;
	
	private ObjectMapper objectMapper;

	public AdministrationJpaAggregateStore(
			ISubscriptionsManager subscriptionsManager, 
			ApplicationEventPublisher applicationEventPublisher,
			AdministrationEventStreamRepository streamRepository) {
		
		this.subscriptionsManager = subscriptionsManager;
		this.applicationEventPublisher = applicationEventPublisher;
		this.streamRepository = streamRepository;
		
		objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
				
				
		domainEvents = new ArrayList<>();

		aggregateSuppliers = new HashMap<>();
		
		aggregateSuppliers.put(com.example.meetup.administration.domain.members.Member.class, 
				() -> new com.example.meetup.administration.domain.members.Member());

		aggregateSuppliers.put(com.example.meetup.administration.domain.meetingGroupProposals.MeetingGroupProposal.class, 
				() -> new com.example.meetup.administration.domain.meetingGroupProposals.MeetingGroupProposal());

		aggregateSuppliers.put(com.example.meetup.administration.domain.administrator.Administrator.class, 
				() -> new com.example.meetup.administration.domain.administrator.Administrator());
			
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
						new AdministrationEventStream(
								streamId,
								0,
								new ArrayList<>()));

			var stream = streamRepository.findByStreamId(streamId);

			for (var event : aggregateToSave.getDomainEvents()) {
				try {
					stream.getEvents().add(
							new AdministrationEvent(
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
	public <T extends Aggregate, V> Optional<T> load(TypedIdValueBase<V> aggregateId, Class<? extends Aggregate> aggregateType) {

		var streamId = "Administration-" + aggregateType.getSimpleName() + "-" + aggregateId.getValue();

		if (streamRepository.findByStreamId(streamId) == null)
			return Optional.empty();
		
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

		return Optional.of((T) aggregate);
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
