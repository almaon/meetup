package com.example.meetup.userAccess.application.sendRegistrationConfirmationLink;
import org.springframework.stereotype.Component;

import com.example.meetup.userAccess.base.application.IEvent;
import com.example.meetup.userAccess.base.application.IEventHandler;
import com.example.meetup.userAccess.base.infrastructure.ICommandDispatcher;
import com.example.meetup.userAccess.domain.userRegistrations.events.NewUserRegisteredEvent;

import lombok.RequiredArgsConstructor;
    
@Component
@RequiredArgsConstructor
public class NewUserRegisteredSendConfirmationLinkEventListener implements IEventHandler<NewUserRegisteredEvent> {

	private final ICommandDispatcher commandDispatcher;

	@Override
	public Class<? extends IEvent> registeredFor() {
		return NewUserRegisteredEvent.class;
	}

	@Override
	public void handle(NewUserRegisteredEvent event) {
		commandDispatcher.executeAsync(new SendRegistrationConfirmationLinkCommand());
	}

}
