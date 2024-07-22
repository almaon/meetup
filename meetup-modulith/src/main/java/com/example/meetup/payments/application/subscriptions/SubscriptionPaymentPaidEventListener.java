package com.example.meetup.payments.application.subscriptions;

import com.example.meetup.payments.base.infrastructure.ICommandDispatcher;
import com.example.meetup.payments.base.application.IEventHandler;
import com.example.meetup.payments.base.application.IEvent;

import com.example.meetup.payments.domain.subscriptionPayments.events.SubscriptionPaymentPaidEvent;

import com.example.meetup.payments.application.subscriptions.CreateSubscriptionCommand;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

    
@Component
@RequiredArgsConstructor
public class SubscriptionPaymentPaidEventListener implements IEventHandler<SubscriptionPaymentPaidEvent> {

	private final ICommandDispatcher commandDispatcher;


	@Override
	public Class<? extends IEvent> registeredFor() {
		return SubscriptionPaymentPaidEvent.class;
	}

	@Override
	public void handle(SubscriptionPaymentPaidEvent event) {

		commandDispatcher.executeCommandSync(
				new CreateSubscriptionCommand(event.getSubscriptionPaymentId()));

	}

}
