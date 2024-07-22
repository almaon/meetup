package com.example.meetup.payments.infrastructure.aggregateStore.jpa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.meetup.payments.base.domain.Aggregate;
import com.example.meetup.payments.base.domain.IAggregateStore;
import com.example.meetup.payments.base.domain.IDomainEvent;
import com.example.meetup.payments.base.domain.TypedIdValueBase;
import com.example.meetup.payments.base.infrastructure.ISubscriptionsManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


@Component
public class PaymentsJpaAggregateStore implements IAggregateStore {

	private ISubscriptionsManager subscriptionsManager;
	
	private ApplicationEventPublisher applicationEventPublisher;
	
	private Map<Class<? extends Aggregate>, Supplier<? extends Aggregate>> aggregateSuppliers;
	
	private List<IDomainEvent> domainEvents;
	
	private PaymentsEventStreamRepository streamRepository;
	
	private ObjectMapper objectMapper;

	public PaymentsJpaAggregateStore(
			ISubscriptionsManager subscriptionsManager, 
			ApplicationEventPublisher applicationEventPublisher,
			PaymentsEventStreamRepository streamRepository) {
		
		this.subscriptionsManager = subscriptionsManager;
		this.applicationEventPublisher = applicationEventPublisher;
		this.streamRepository = streamRepository;
		
		objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
				
		domainEvents = new ArrayList<>();

		aggregateSuppliers = new HashMap<>();
		
		aggregateSuppliers.put(com.example.meetup.payments.domain.meetingFeePayments.MeetingFeePayment.class, 
				() -> new com.example.meetup.payments.domain.meetingFeePayments.MeetingFeePayment());

		aggregateSuppliers.put(com.example.meetup.payments.domain.meetingFees.MeetingFee.class, 
				() -> new com.example.meetup.payments.domain.meetingFees.MeetingFee());

		aggregateSuppliers.put(com.example.meetup.payments.domain.payers.Payer.class, 
				() -> new com.example.meetup.payments.domain.payers.Payer());

		aggregateSuppliers.put(com.example.meetup.payments.domain.priceListItems.PriceListItem.class, 
				() -> new com.example.meetup.payments.domain.priceListItems.PriceListItem());

		aggregateSuppliers.put(com.example.meetup.payments.domain.subscriptionPayments.SubscriptionPayment.class, 
				() -> new com.example.meetup.payments.domain.subscriptionPayments.SubscriptionPayment());

		aggregateSuppliers.put(com.example.meetup.payments.domain.subscriptionRenewalPayments.SubscriptionRenewalPayment.class, 
				() -> new com.example.meetup.payments.domain.subscriptionRenewalPayments.SubscriptionRenewalPayment());

		aggregateSuppliers.put(com.example.meetup.payments.domain.subscriptions.Subscription.class, 
				() -> new com.example.meetup.payments.domain.subscriptions.Subscription());
			
	}

	@Transactional
	@Override
	public void save(Aggregate... aggregates) {

		for (var aggregateToSave : aggregates) {

			var streamId = aggregateToSave.getStreamId();

			if (streamRepository.findByStreamId(streamId) == null)
				streamRepository.save(
						new PaymentsEventStream(
								streamId,
								new ArrayList<>()));

			var stream = streamRepository.findByStreamId(streamId);

			for (var event : aggregateToSave.getDomainEvents()) {
				try {
					stream.getEvents().add(
							new PaymentsEvent(
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

		var streamId = "Payments-" + aggregateType.getSimpleName() + "-" + aggregateId.getValue();

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
