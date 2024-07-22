
package com.example.meetup.meetings.application.members;

import com.example.meetup.meetings.base.infrastructure.ICommandDispatcher;
import com.example.meetup.meetings.base.application.IEventHandler;
import com.example.meetup.meetings.base.application.IEvent;
import com.example.meetup.meetings.application.integration.listen.userCreated.UserCreatedMeetingsIntegrationEvent;

import com.example.meetup.meetings.application.members.CreateMemberCommand;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

    
@Component
@RequiredArgsConstructor
public class UserCreatedMeetingsIntegrationEventListener implements IEventHandler<UserCreatedMeetingsIntegrationEvent> {

	private final ICommandDispatcher commandDispatcher;


	@Override
	public Class<? extends IEvent> registeredFor() {
		return UserCreatedMeetingsIntegrationEvent.class;
	}

	@Override
	public void handle(UserCreatedMeetingsIntegrationEvent event) {

		commandDispatcher.executeCommandAsync(
			new CreateMemberCommand(
				event.getUserId(),
				event.getLogin(),
				event.getLastName(),
				event.getFirstName(),
				event.getEmail(),
				event.getRegisterDate()));

	}

}
