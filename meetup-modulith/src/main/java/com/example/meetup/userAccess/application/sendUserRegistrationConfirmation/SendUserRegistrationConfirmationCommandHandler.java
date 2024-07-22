package com.example.meetup.userAccess.application.sendUserRegistrationConfirmation;


import com.example.meetup.userAccess.base.application.IAsyncCommandHandler;
import com.example.meetup.userAccess.base.domain.IAggregateStore;





import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

    
@Component
@RequiredArgsConstructor
public class SendUserRegistrationConfirmationCommandHandler implements IAsyncCommandHandler<SendUserRegistrationConfirmationCommand> {

	private final IAggregateStore aggregateStore;


	@Override
	public void handle(SendUserRegistrationConfirmationCommand command) {

		/* implement:
    	
    	
		
		aggregateStore.save();
		*/

	}	
    	
}
