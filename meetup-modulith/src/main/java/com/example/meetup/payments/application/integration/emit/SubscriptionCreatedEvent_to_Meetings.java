package com.example.meetup.payments.application.integration.emit;

import com.example.meetup.payments.base.application.IEventHandler;
import com.example.meetup.payments.base.application.IEvent;

import com.example.meetup.meetings.application.integration.listen.subscriptionCreated.SubscriptionCreatedMeetingsIntegrationEvent;
import com.example.meetup.payments.domain.subscriptions.events.SubscriptionCreatedEvent;

import org.springframework.context.ApplicationEventPublisher;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;


@Component
@RequiredArgsConstructor
public class SubscriptionCreatedEvent_to_Meetings implements IEventHandler<SubscriptionCreatedEvent> {


	public final ApplicationEventPublisher applicationEventPublisher;

	@Override
	public Class<? extends IEvent> registeredFor() {
		return SubscriptionCreatedEvent.class;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void handle(SubscriptionCreatedEvent event) {

		var integrationEvent = mapToIntegrationEvent(event);
		applicationEventPublisher.publishEvent(integrationEvent);
	}
	
	private com.example.meetup.meetings.application.integration.listen.subscriptionCreated.SubscriptionCreatedMeetingsIntegrationEvent mapToIntegrationEvent(com.example.meetup.payments.domain.subscriptions.events.SubscriptionCreatedEvent sourceEvent) {
		var targetInstance = new com.example.meetup.meetings.application.integration.listen.subscriptionCreated.SubscriptionCreatedMeetingsIntegrationEvent();
		targetInstance.setSubscriptionId(sourceEvent.getSubscriptionId());
		targetInstance.setPayerId(sourceEvent.getPayerId());
		targetInstance.setSubscriptionPaymentId(sourceEvent.getSubscriptionPaymentId());
		targetInstance.setSubscriptionPeriodCode(sourceEvent.getSubscriptionPeriodCode());
		targetInstance.setCountryCode(sourceEvent.getCountryCode());
		targetInstance.setStatus(sourceEvent.getStatus());
		targetInstance.setExpirationDate(sourceEvent.getExpirationDate());
		return targetInstance;
	}


}





