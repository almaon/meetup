package com.example.meetup.payments.application.subscriptions;

import org.springframework.stereotype.Component;

import com.example.meetup.payments.application.payers.view.GetPayerByIdQuery;
import com.example.meetup.payments.application.payers.view.PayerView;
import com.example.meetup.payments.application.subscriptions.view.GetSubscriptionPaymentsByIdQuery;
import com.example.meetup.payments.application.subscriptions.view.SubscriptionPaymentsView;
import com.example.meetup.payments.base.application.IEvent;
import com.example.meetup.payments.base.application.IEventHandler;
import com.example.meetup.payments.base.infrastructure.ICommandDispatcher;
import com.example.meetup.payments.domain.subscriptionRenewalPayments.events.SubscriptionRenewalPaymentPaidEvent;
import com.example.meetup.payments.infrastructure.PaymentsQueryDispatcher;

import lombok.RequiredArgsConstructor;

    
@Component
@RequiredArgsConstructor
public class SubscriptionRenewalPaymentPaidEventListener implements IEventHandler<SubscriptionRenewalPaymentPaidEvent> {

	private final ICommandDispatcher commandDispatcher;

	private final PaymentsQueryDispatcher queryDispatcher;

	@Override
	public Class<? extends IEvent> registeredFor() {
		return SubscriptionRenewalPaymentPaidEvent.class;
	}

	@Override
	public void handle(SubscriptionRenewalPaymentPaidEvent event) {

		SubscriptionPaymentsView paymentsView = queryDispatcher.executeQuery(new GetSubscriptionPaymentsByIdQuery(event.getSubscriptionRenewalPaymentId()));
		PayerView subscriber = queryDispatcher.executeQuery(new GetPayerByIdQuery(paymentsView.getPayerId()));

		commandDispatcher.executeCommandAsync(new SendSubscriptionRenewalConfirmationEmailCommand(
		                                      	event.getSubscriptionId(),
		                                      	subscriber.getEmail()));

	}

}
