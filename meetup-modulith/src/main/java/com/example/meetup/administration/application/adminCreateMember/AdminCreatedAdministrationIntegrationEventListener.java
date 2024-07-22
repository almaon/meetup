package com.example.meetup.administration.application.adminCreateMember;

import org.springframework.stereotype.Component;

import com.example.meetup.administration.application.integration.listen.userCreated.UserCreatedAdministrationIntegrationEvent;
import com.example.meetup.administration.application.integration.listen.userCreated.UserType;
import com.example.meetup.administration.base.application.IEvent;
import com.example.meetup.administration.base.application.IEventHandler;
import com.example.meetup.administration.base.infrastructure.ICommandDispatcher;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AdminCreatedAdministrationIntegrationEventListener implements IEventHandler<UserCreatedAdministrationIntegrationEvent> {

	private final ICommandDispatcher commandDispatcher;

	@Override
	public Class<? extends IEvent> registeredFor() {
		return UserCreatedAdministrationIntegrationEvent.class;
	}

	@Override
	public void handle(UserCreatedAdministrationIntegrationEvent event) {

		if (event.getUserType() == UserType.admin) {
			commandDispatcher.executeCommandAsync(
					new AdminCreateMemberCommand(
							event.getUserId(),
							event.getLogin(),
							event.getEmail(),
							event.getFirstName(),
							event.getLastName(),
							event.getRegisterDate()));
		}
	}

}
