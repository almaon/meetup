package com.example.meetup.payments.application.subscriptions;


import com.example.meetup.payments.base.application.IAsyncCommandHandler;
import com.example.meetup.payments.base.domain.IAggregateStore;





import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

    
@Component
@RequiredArgsConstructor
public class SendSubscriptionRenewalConfirmationEmailCommandHandler implements IAsyncCommandHandler<SendSubscriptionRenewalConfirmationEmailCommand> {

	private final IAggregateStore aggregateStore;


	@Override
	public void handle(SendSubscriptionRenewalConfirmationEmailCommand command) {

		// TODO:send email

	}	
    	
}
