package com.example.meetup.userAccess.infrastructure.aggregateStore.jpa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.meetup.userAccess.base.domain.Aggregate;
import com.example.meetup.userAccess.base.domain.IAggregateStore;
import com.example.meetup.userAccess.base.domain.IDomainEvent;
import com.example.meetup.userAccess.base.domain.TypedIdValueBase;
import com.example.meetup.userAccess.base.infrastructure.ISubscriptionsManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


@Component
public class UserAccessJpaAggregateStore implements IAggregateStore {

	private ISubscriptionsManager subscriptionsManager;
	
	private ApplicationEventPublisher applicationEventPublisher;
	
	private Map<Class<? extends Aggregate>, Supplier<? extends Aggregate>> aggregateSuppliers;
	
	private List<IDomainEvent> domainEvents;
	
	private UserAccessEventStreamRepository streamRepository;
	
	private ObjectMapper objectMapper;

	public UserAccessJpaAggregateStore(
			ISubscriptionsManager subscriptionsManager, 
			ApplicationEventPublisher applicationEventPublisher,
			UserAccessEventStreamRepository streamRepository) {
		
		this.subscriptionsManager = subscriptionsManager;
		this.applicationEventPublisher = applicationEventPublisher;
		this.streamRepository = streamRepository;
		
		objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
				
		domainEvents = new ArrayList<>();

		aggregateSuppliers = new HashMap<>();
		
		aggregateSuppliers.put(com.example.meetup.userAccess.domain.userRegistrations.UserRegistration.class, 
				() -> new com.example.meetup.userAccess.domain.userRegistrations.UserRegistration());
				
		aggregateSuppliers.put(com.example.meetup.userAccess.domain.users.User.class, 
				() -> new com.example.meetup.userAccess.domain.users.User());
	}

	@Transactional
	@Override
	public void save(Aggregate... aggregates) {

		for (var aggregateToSave : aggregates) {

			var streamId = aggregateToSave.getStreamId();

			if (streamRepository.findByStreamId(streamId) == null)
				streamRepository.save(
						new UserAccessEventStream(
								streamId,
								new ArrayList<>()));

			var stream = streamRepository.findByStreamId(streamId);

			for (var event : aggregateToSave.getDomainEvents()) {
				try {
					stream.getEvents().add(
							new UserAccessEvent(
									event.getId(),
									event.getClass().getName(),
									event.occuredOn(),
									objectMapper.writeValueAsString(event)));
				} catch (JsonProcessingException e) {
					throw new RuntimeException(e.getMessage());
				}

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

		var streamId = "UserAccess-" + aggregateType.getSimpleName() + "-" + aggregateId.getValue();

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
