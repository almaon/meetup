package com.example.meetup.payments.application.subscriptions;

import com.example.meetup.payments.base.infrastructure.ICommandDispatcher;
import com.example.meetup.payments.base.application.IEventHandler;
import com.example.meetup.payments.base.application.IEvent;

import com.example.meetup.payments.domain.subscriptionPayments.events.SubscriptionPaymentCreatedEvent;

import com.example.meetup.payments.application.subscriptions.ExecuteSubscriptionPaymentCommand;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

    
@Component
@RequiredArgsConstructor
public class SubscriptionPaymentCreatedEventListener implements IEventHandler<SubscriptionPaymentCreatedEvent> {

	private final ICommandDispatcher commandDispatcher;


	@Override
	public Class<? extends IEvent> registeredFor() {
		return SubscriptionPaymentCreatedEvent.class;
	}

	@Override
	public void handle(SubscriptionPaymentCreatedEvent event) {

		commandDispatcher.executeCommandAsync(new ExecuteSubscriptionPaymentCommand());

	}

}
