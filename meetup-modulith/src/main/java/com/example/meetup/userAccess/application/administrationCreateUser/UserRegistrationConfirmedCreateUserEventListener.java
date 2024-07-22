package com.example.meetup.userAccess.application.administrationCreateUser;

import org.springframework.stereotype.Component;

import com.example.meetup.userAccess.base.application.IEvent;
import com.example.meetup.userAccess.base.application.IEventHandler;
import com.example.meetup.userAccess.base.infrastructure.ICommandDispatcher;
import com.example.meetup.userAccess.domain.userRegistrations.events.UserRegistrationConfirmedEvent;

import lombok.RequiredArgsConstructor;

    
@Component
@RequiredArgsConstructor
public class UserRegistrationConfirmedCreateUserEventListener implements IEventHandler<UserRegistrationConfirmedEvent> {

	private final ICommandDispatcher commandDispatcher;

	
	@Override
	public Class<? extends IEvent> registeredFor() {
		return UserRegistrationConfirmedEvent.class;
	}
	

	@Override
	public void handle(UserRegistrationConfirmedEvent event) {

		commandDispatcher.executeSync(
				new AdministrationCreateUserCommand(
						event.getRegisterDate(), 
						event.getLogin(), 
						event.getLastName(), 
						event.getFirstName(), 
						event.getEmail()));
	}

}
