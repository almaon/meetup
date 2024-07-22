package com.example.meetup.payments.application.subscriptions.view;


import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import com.example.meetup.payments.base.application.IProjector;
import com.example.meetup.payments.base.application.IEvent;

import com.example.meetup.payments.domain.subscriptions.events.SubscriptionExpiredEvent;
import com.example.meetup.payments.domain.subscriptions.events.SubscriptionCreatedEvent;
import com.example.meetup.payments.domain.subscriptions.events.SubscriptionRenewedEvent;

import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class SubscriptionsProjector implements IProjector {

	private final SubscriptionsRepository subscriptionsRepository;
	


	
	private void when(SubscriptionExpiredEvent event) {

		var view = subscriptionsRepository.findBySubscriptionId(event.getSubscriptionId());
		
		view.setStatus(event.getStatus());

	}
	private void when(SubscriptionRenewedEvent event) {

		var view = subscriptionsRepository.findBySubscriptionId(event.getSubscriptionId());
		
		view.setStatus(event.getStatus());
		view.setExpirationDate(event.getExpirationDate());

	}
	private void when(SubscriptionCreatedEvent event) {

		subscriptionsRepository.save(
			new SubscriptionsView(
				event.getSubscriptionId(),
				event.getSubscriptionPeriodCode(),
				event.getStatus(),
				event.getExpirationDate()));

	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void project(IEvent event) {
		
		if (event instanceof SubscriptionExpiredEvent castedEvent) {
			when(castedEvent);
			return;
		}
		if (event instanceof SubscriptionRenewedEvent castedEvent) {
			when(castedEvent);
			return;
		}
		if (event instanceof SubscriptionCreatedEvent castedEvent) {
			when(castedEvent);
			return;
		}
			
	}
	
}
