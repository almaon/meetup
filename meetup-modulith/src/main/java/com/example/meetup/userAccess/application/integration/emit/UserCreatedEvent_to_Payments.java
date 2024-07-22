package com.example.meetup.userAccess.application.integration.emit;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.meetup.userAccess.base.application.IEvent;
import com.example.meetup.userAccess.base.application.IEventHandler;
import com.example.meetup.userAccess.domain.users.events.UserCreatedEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserCreatedEvent_to_Payments implements IEventHandler<UserCreatedEvent> {

	public final ApplicationEventPublisher applicationEventPublisher;

	
	@Override
	public Class<? extends IEvent> registeredFor() {
		return UserCreatedEvent.class;
	}

	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void handle(UserCreatedEvent event) {

		var integrationEvent = mapToIntegrationEvent(event);
		applicationEventPublisher.publishEvent(integrationEvent);
	}
	
	
	private com.example.meetup.payments.application.integration.listen.userCreated.UserCreatedPaymentsIntegrationEvent mapToIntegrationEvent(com.example.meetup.userAccess.domain.users.events.UserCreatedEvent sourceEvent) {
		
		var targetInstance = new com.example.meetup.payments.application.integration.listen.userCreated
				.UserCreatedPaymentsIntegrationEvent();
		
		targetInstance.setUserId(sourceEvent.getUserId());
		targetInstance.setFirstName(sourceEvent.getFirstName());
		targetInstance.setRegisterDate(sourceEvent.getRegisterDate());
		targetInstance.setLastName(sourceEvent.getLastName());
		targetInstance.setLogin(sourceEvent.getLogin());
		targetInstance.setEmail(sourceEvent.getEmail());
		
		targetInstance.setUserType(com.example.meetup.payments.application.integration.listen.userCreated
				.UserType.valueOf(sourceEvent.getUserType().name()));
		
		return targetInstance;
	}

}





