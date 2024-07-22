package com.example.meetup.payments.application.subscriptions;

import org.springframework.stereotype.Component;

import com.example.meetup.payments.application.payers.view.GetPayerByIdQuery;
import com.example.meetup.payments.application.payers.view.PayerView;
import com.example.meetup.payments.base.application.IEvent;
import com.example.meetup.payments.base.application.IEventHandler;
import com.example.meetup.payments.base.infrastructure.ICommandDispatcher;
import com.example.meetup.payments.domain.subscriptions.events.SubscriptionCreatedEvent;
import com.example.meetup.payments.infrastructure.PaymentsQueryDispatcher;

import lombok.RequiredArgsConstructor;

    
@Component
@RequiredArgsConstructor
public class SubscriptionCreatedEventListener implements IEventHandler<SubscriptionCreatedEvent> {

	private final ICommandDispatcher commandDispatcher;

	private final PaymentsQueryDispatcher queryDispatcher;

	@Override
	public Class<? extends IEvent> registeredFor() {
		return SubscriptionCreatedEvent.class;
	}

	@Override
	public void handle(SubscriptionCreatedEvent event) {

		PayerView subscriber = queryDispatcher.executeQuery(new GetPayerByIdQuery(event.getPayerId()));
		
		commandDispatcher.executeCommandAsync(
				new SendSubscriptionCreationConfirmationEmailCommand(
						event.getSubscriptionId(),
						subscriber.getEmail()));

	}

}
