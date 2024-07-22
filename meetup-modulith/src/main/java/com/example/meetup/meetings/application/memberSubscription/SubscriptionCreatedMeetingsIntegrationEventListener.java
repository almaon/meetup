
package com.example.meetup.meetings.application.memberSubscription;

import com.example.meetup.meetings.base.infrastructure.ICommandDispatcher;
import com.example.meetup.meetings.base.application.IEventHandler;
import com.example.meetup.meetings.base.application.IEvent;
import com.example.meetup.meetings.application.integration.listen.subscriptionCreated.SubscriptionCreatedMeetingsIntegrationEvent;

import com.example.meetup.meetings.application.memberSubscription.CreateSubscriptionForMemberCommand;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

    
@Component
@RequiredArgsConstructor
public class SubscriptionCreatedMeetingsIntegrationEventListener implements IEventHandler<SubscriptionCreatedMeetingsIntegrationEvent> {

	private final ICommandDispatcher commandDispatcher;


	@Override
	public Class<? extends IEvent> registeredFor() {
		return SubscriptionCreatedMeetingsIntegrationEvent.class;
	}

	@Override
	public void handle(SubscriptionCreatedMeetingsIntegrationEvent event) {

		commandDispatcher.executeCommandAsync(
			new CreateSubscriptionForMemberCommand(
				event.getPayerId(),
				event.getExpirationDate()));

	}

}
