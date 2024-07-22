package com.example.meetup.userAccess.application.confirmUserRegistration;

import org.springframework.stereotype.Component;

import com.example.meetup.userAccess.application.confirmationLinks.view.ConfirmationLinksView;
import com.example.meetup.userAccess.application.confirmationLinks.view.GetConfirmationLinksByIdQuery;
import com.example.meetup.userAccess.base.application.IAsyncCommandHandler;
import com.example.meetup.userAccess.base.domain.IAggregateStore;
import com.example.meetup.userAccess.base.infrastructure.IQueryDispatcher;
import com.example.meetup.userAccess.domain.userRegistrations.UserRegistration;
import com.example.meetup.userAccess.domain.userRegistrations.UserRegistrationId;

import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
    
@Component
@RequiredArgsConstructor
public class ConfirmUserRegistrationCommandHandler implements IAsyncCommandHandler<ConfirmUserRegistrationCommand> {
	
	private final IAggregateStore aggregateStore;
	private final IQueryDispatcher queryDispatcher;
	
	
	@Override
	public void handle(ConfirmUserRegistrationCommand command) {	

			ConfirmationLinksView confirmationLinksView = queryDispatcher.execute(new GetConfirmationLinksByIdQuery(command.getConfirmLink()));
			
			if (confirmationLinksView == null)
				throw new NotFoundException("invalid confirmation link");

			UserRegistration userRegistrations = aggregateStore.load(new UserRegistrationId(confirmationLinksView.getUserRegistrationId()), UserRegistration.class);
			
			userRegistrations.confirmRegistration();
			
			aggregateStore.save(userRegistrations);		
	}	
    	
}
