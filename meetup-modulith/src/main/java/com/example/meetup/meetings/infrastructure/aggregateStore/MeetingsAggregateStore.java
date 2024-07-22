package com.example.meetup.meetings.infrastructure.aggregateStore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;

import com.eventstore.dbclient.AppendToStreamOptions;
import com.eventstore.dbclient.EventDataBuilder;
import com.eventstore.dbclient.EventStoreDBClient;
import com.eventstore.dbclient.EventStoreDBClientSettings;
import com.eventstore.dbclient.EventStoreDBConnectionString;
import com.eventstore.dbclient.ExpectedRevision;
import com.eventstore.dbclient.ReadResult;
import com.eventstore.dbclient.ReadStreamOptions;
import com.eventstore.dbclient.ResolvedEvent;
import com.eventstore.dbclient.StreamNotFoundException;
import com.example.meetup.infrastructure.eventstore.EventTypeMapper;
import com.example.meetup.meetings.base.domain.Aggregate;
import com.example.meetup.meetings.base.domain.IAggregateStore;
import com.example.meetup.meetings.base.domain.IDomainEvent;
import com.example.meetup.meetings.base.domain.TypedIdValueBase;
import com.example.meetup.meetings.base.infrastructure.ISubscriptionsManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


//@Component
public class MeetingsAggregateStore implements IAggregateStore {

	private ISubscriptionsManager subscriptionsManager;

	private ApplicationEventPublisher applicationEventPublisher;

	private Map<Class<? extends Aggregate>, Supplier<? extends Aggregate>> aggregateSuppliers;

	private List<IDomainEvent> domainEvents;

	private EventStoreDBClient eventStore;

	private ObjectMapper mapper =
			new JsonMapper()
			.registerModule(new JavaTimeModule())
			.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
			.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

	public MeetingsAggregateStore(
			ISubscriptionsManager subscriptionsManager,
			ApplicationEventPublisher applicationEventPublisher, 
			@Value("${esdb.connectionstring}") String connectionString) {

		EventStoreDBClientSettings settings = EventStoreDBConnectionString.parseOrThrow(connectionString);

		eventStore = EventStoreDBClient.create(settings);

		this.subscriptionsManager = subscriptionsManager;
		this.applicationEventPublisher = applicationEventPublisher;

		domainEvents = new ArrayList<>();

		aggregateSuppliers = new HashMap<>();

		aggregateSuppliers.put(com.example.meetup.meetings.domain.meetingGroupProposal.MeetingGroupProposal.class, 
				() -> new com.example.meetup.meetings.domain.meetingGroupProposal.MeetingGroupProposal());

		aggregateSuppliers.put(com.example.meetup.meetings.domain.meetingCommentingConfiguration.MeetingCommentingConfiguration.class, 
				() -> new com.example.meetup.meetings.domain.meetingCommentingConfiguration.MeetingCommentingConfiguration());

		aggregateSuppliers.put(com.example.meetup.meetings.domain.meetingComment.MeetingComment.class, 
				() -> new com.example.meetup.meetings.domain.meetingComment.MeetingComment());

		aggregateSuppliers.put(com.example.meetup.meetings.domain.meetingGroup.MeetingGroup.class, 
				() -> new com.example.meetup.meetings.domain.meetingGroup.MeetingGroup());

		aggregateSuppliers.put(com.example.meetup.meetings.domain.meetingMemberCommentLike.MeetingMemberCommentLike.class, 
				() -> new com.example.meetup.meetings.domain.meetingMemberCommentLike.MeetingMemberCommentLike());

		aggregateSuppliers.put(com.example.meetup.meetings.domain.meeting.Meeting.class, 
				() -> new com.example.meetup.meetings.domain.meeting.Meeting());

		aggregateSuppliers.put(com.example.meetup.meetings.domain.member.Member.class, 
				() -> new com.example.meetup.meetings.domain.member.Member());

		aggregateSuppliers.put(com.example.meetup.meetings.domain.memberSubscriptions.MemberSubscription.class, 
				() -> new com.example.meetup.meetings.domain.memberSubscriptions.MemberSubscription());

	}

	@Override
	public void save(Aggregate... aggregates) {

		for (var aggregateToSave : aggregates) {

			var streamId = aggregateToSave.getStreamId();

			var events = aggregateToSave.getDomainEvents().stream()
					.map(event -> {
						try {
							return EventDataBuilder.json(
									UUID.randomUUID(),
									EventTypeMapper.toName(event.getClass()),
									mapper.writeValueAsBytes(event)
									).build();
						} catch (JsonProcessingException e) {
							throw new RuntimeException(e);
						}
					});

			try {
				var result = eventStore.appendToStream(
						streamId,
						AppendToStreamOptions.get().expectedRevision(ExpectedRevision.any()),
						events.iterator()).get();
				result.getNextExpectedRevision();
			} catch (Throwable e) {
				throw new RuntimeException(e);
			}

			for (var event : aggregateToSave.getDomainEvents()) {
				subscriptionsManager.addEvent(event);
				domainEvents.add(event);
			}

			for (var event : aggregateToSave.getDomainEvents()) {
				applicationEventPublisher.publishEvent(event);
			}
		}
	}

	@Override
	public <T extends Aggregate, V> T load(TypedIdValueBase<V> aggregateId, Class<? extends Aggregate> aggregateType) {

		var streamId = "Meetings-" + aggregateType.getSimpleName() + "-" + aggregateId.getValue();

		var events = getEvents(streamId);

		var aggregate = aggregateSuppliers.get(aggregateType).get();

		aggregate.load(events);

		return (T) aggregate;
	}

	private List<IDomainEvent> getEvents(String streamId) {

		ReadResult result;
		
		try {
			result = eventStore.readStream(streamId, ReadStreamOptions.get()).get();
		} catch (Throwable e) {
			Throwable innerException = e.getCause();

			if (innerException instanceof StreamNotFoundException) {
				return null;
			}
			throw new RuntimeException(e);
		}

		var events = result.getEvents().stream()
				.map(resolvedEvent -> {
					var eventClass = EventTypeMapper.toClass(resolvedEvent.getEvent().getEventType());

					if (eventClass.isEmpty())
						return Optional.empty();

					return deserialize(eventClass.get(), resolvedEvent);
				})
				.filter(Optional::isPresent)
				.map(Optional::get)
				.map(event -> (IDomainEvent) event)
				.toList();

		return events;
	}

	public <Event> Optional<Event> deserialize(Class<Event> eventClass, ResolvedEvent resolvedEvent) {
		try {
			var result = mapper.readValue(resolvedEvent.getEvent().getEventData(), eventClass);

			if (result == null)
				return Optional.empty();

			return Optional.of(result);
		} catch (IOException e) {
			return Optional.empty();
		}
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
