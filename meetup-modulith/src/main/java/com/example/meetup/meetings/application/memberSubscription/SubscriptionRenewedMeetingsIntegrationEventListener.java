
package com.example.meetup.meetings.application.memberSubscription;

import com.example.meetup.meetings.base.infrastructure.ICommandDispatcher;
import com.example.meetup.meetings.base.application.IEventHandler;
import com.example.meetup.meetings.base.application.IEvent;
import com.example.meetup.meetings.application.integration.listen.subscriptionRenewed.SubscriptionRenewedMeetingsIntegrationEvent;

import com.example.meetup.meetings.application.memberSubscription.ChangeSubscriptionExpirationDateForMemberCommand;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

    
@Component
@RequiredArgsConstructor
public class SubscriptionRenewedMeetingsIntegrationEventListener implements IEventHandler<SubscriptionRenewedMeetingsIntegrationEvent> {

	private final ICommandDispatcher commandDispatcher;


	@Override
	public Class<? extends IEvent> registeredFor() {
		return SubscriptionRenewedMeetingsIntegrationEvent.class;
	}

	@Override
	public void handle(SubscriptionRenewedMeetingsIntegrationEvent event) {

		commandDispatcher.executeCommandAsync(
			new ChangeSubscriptionExpirationDateForMemberCommand(
				event.getPayerId(),
				event.getExpirationDate()));

	}

}
