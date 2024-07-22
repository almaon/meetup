package com.example.meetup.payments.application.integration.emit;

import com.example.meetup.payments.base.application.IEventHandler;
import com.example.meetup.payments.base.application.IEvent;

import com.example.meetup.meetings.application.integration.listen.subscriptionRenewed.SubscriptionRenewedMeetingsIntegrationEvent;
import com.example.meetup.payments.domain.subscriptions.events.SubscriptionRenewedEvent;

import org.springframework.context.ApplicationEventPublisher;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;


@Component
@RequiredArgsConstructor
public class SubscriptionRenewedEvent_to_Meetings implements IEventHandler<SubscriptionRenewedEvent> {


	public final ApplicationEventPublisher applicationEventPublisher;

	@Override
	public Class<? extends IEvent> registeredFor() {
		return SubscriptionRenewedEvent.class;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void handle(SubscriptionRenewedEvent event) {

		var integrationEvent = mapToIntegrationEvent(event);
		applicationEventPublisher.publishEvent(integrationEvent);
	}
	
	private com.example.meetup.meetings.application.integration.listen.subscriptionRenewed.SubscriptionRenewedMeetingsIntegrationEvent mapToIntegrationEvent(com.example.meetup.payments.domain.subscriptions.events.SubscriptionRenewedEvent sourceEvent) {
		var targetInstance = new com.example.meetup.meetings.application.integration.listen.subscriptionRenewed.SubscriptionRenewedMeetingsIntegrationEvent();
		targetInstance.setSubscriptionId(sourceEvent.getSubscriptionId());
		targetInstance.setStatus(sourceEvent.getStatus());
		targetInstance.setExpirationDate(sourceEvent.getExpirationDate());
		targetInstance.setPayerId(sourceEvent.getPayerId());
		targetInstance.setSubscriptionPeriodCode(sourceEvent.getSubscriptionPeriodCode());
		return targetInstance;
	}


}





