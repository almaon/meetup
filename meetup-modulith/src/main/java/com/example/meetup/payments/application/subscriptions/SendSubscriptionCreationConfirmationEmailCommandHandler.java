package com.example.meetup.payments.application.subscriptions;


import com.example.meetup.payments.base.application.IAsyncCommandHandler;
import com.example.meetup.payments.base.domain.IAggregateStore;





import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

    
@Component
@RequiredArgsConstructor
public class SendSubscriptionCreationConfirmationEmailCommandHandler implements IAsyncCommandHandler<SendSubscriptionCreationConfirmationEmailCommand> {

	private final IAggregateStore aggregateStore;


	@Override
	public void handle(SendSubscriptionCreationConfirmationEmailCommand command) {

		// TODO:send email
		

	}	
    	
}
