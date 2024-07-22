package com.example.meetup.userAccess.application.addUserToAuthServer;

import com.example.meetup.userAccess.base.infrastructure.ICommandDispatcher;
import com.example.meetup.userAccess.base.application.IEventHandler;
import com.example.meetup.userAccess.base.application.IEvent;

import com.example.meetup.userAccess.domain.userRegistrations.events.UserRegistrationConfirmedEvent;

import com.example.meetup.userAccess.application.addUserToAuthServer.AddUserToAuthServerCommand;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

    
@Component
@RequiredArgsConstructor
public class UserRegistrationConfirmedAddUserToAuthServerEventListener implements IEventHandler<UserRegistrationConfirmedEvent> {

	private final ICommandDispatcher commandDispatcher;

	@Override
	public Class<? extends IEvent> registeredFor() {
		return UserRegistrationConfirmedEvent.class;
	}

	@Override
	public void handle(UserRegistrationConfirmedEvent event) {

		commandDispatcher.executeAsync(new AddUserToAuthServerCommand(
				event.getUserRegistrationId(),
				event.getFirstName(),
				event.getRegisterDate(),
				event.getEncodedPassword(),
				event.getLastName(),
				event.getEmail(),
				event.getLogin()));

	}

}
