package com.example.meetup.payments.application.payers;

import org.springframework.stereotype.Component;

import com.example.meetup.payments.application.integration.listen.userCreated.UserCreatedPaymentsIntegrationEvent;
import com.example.meetup.payments.base.application.IEvent;
import com.example.meetup.payments.base.application.IEventHandler;
import com.example.meetup.payments.base.infrastructure.ICommandDispatcher;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserCreatedPaymentsIntegrationEventListener implements IEventHandler<UserCreatedPaymentsIntegrationEvent> {

	private final ICommandDispatcher commandDispatcher;

	@Override
	public Class<? extends IEvent> registeredFor() {
		return UserCreatedPaymentsIntegrationEvent.class;
	}

	@Override
	public void handle(UserCreatedPaymentsIntegrationEvent event) {

		commandDispatcher.executeCommandSync(
				new CreatePayerCommand(
						event.getUserId(),
						event.getLastName(),
						event.getEmail(),
						event.getLogin(),
						event.getFirstName(),
						event.getRegisterDate()));
	}

}
