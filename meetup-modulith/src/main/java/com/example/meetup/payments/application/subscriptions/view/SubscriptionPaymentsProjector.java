package com.example.meetup.payments.application.subscriptions.view;


import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import com.example.meetup.payments.base.application.IProjector;
import com.example.meetup.payments.base.application.IEvent;

import com.example.meetup.payments.domain.subscriptionRenewalPayments.events.SubscriptionRenewalPaymentPaidEvent;
import com.example.meetup.payments.domain.subscriptionPayments.events.SubscriptionPaymentExpiredEvent;
import com.example.meetup.payments.domain.subscriptionPayments.events.SubscriptionPaymentPaidEvent;
import com.example.meetup.payments.domain.subscriptionRenewalPayments.events.SubscriptionRenewalPaymentCreatedEvent;
import com.example.meetup.payments.domain.subscriptionPayments.events.SubscriptionPaymentCreatedEvent;

import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class SubscriptionPaymentsProjector implements IProjector {

	private final SubscriptionPaymentsRepository subscriptionPaymentsRepository;
	


	
	private void when(SubscriptionPaymentCreatedEvent event) {

		subscriptionPaymentsRepository.save(
			new SubscriptionPaymentsView(
				event.getSubscriptionPaymentId(),
				event.getPayerId(),
				"Initial Payment",
				event.getStatus(),
				event.occuredOn(),
				null,
				event.getValue(),
				event.getCurrency(),
				event.getSubscriptionPeriodCode()));

	}
	private void when(SubscriptionRenewalPaymentCreatedEvent event) {

		subscriptionPaymentsRepository.save(
				new SubscriptionPaymentsView(
					event.getSubscriptionRenewalPaymentId(),
					event.getPayerId(),
					"Renewal Payment",
					event.getStatus(),
					event.occuredOn(),
					event.getSubscriptionId(),
					event.getValue(),
					event.getCurrency(),
					event.getSubscriptionPeriodCode()));

	}
	private void when(SubscriptionRenewalPaymentPaidEvent event) {

		var view = subscriptionPaymentsRepository.findByPaymentId(event.getSubscriptionRenewalPaymentId());
		
		view.setStatus(event.getStatus());

	}
	private void when(SubscriptionPaymentPaidEvent event) {

		var view = subscriptionPaymentsRepository.findByPaymentId(event.getSubscriptionPaymentId());
		
		view.setStatus(event.getStatus());

	}
	private void when(SubscriptionPaymentExpiredEvent event) {

		var view = subscriptionPaymentsRepository.findByPaymentId(event.getSubscriptionPaymentId());
		
		view.setStatus(event.getStatus());

	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void project(IEvent event) {
		
		if (event instanceof SubscriptionPaymentCreatedEvent castedEvent) {
			when(castedEvent);
			return;
		}
		if (event instanceof SubscriptionRenewalPaymentCreatedEvent castedEvent) {
			when(castedEvent);
			return;
		}
		if (event instanceof SubscriptionRenewalPaymentPaidEvent castedEvent) {
			when(castedEvent);
			return;
		}
		if (event instanceof SubscriptionPaymentPaidEvent castedEvent) {
			when(castedEvent);
			return;
		}
		if (event instanceof SubscriptionPaymentExpiredEvent castedEvent) {
			when(castedEvent);
			return;
		}
			
	}
	
}
