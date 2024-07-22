package com.example.meetup.payments.application.subscriptions;


import com.example.meetup.payments.base.application.IAsyncCommandHandler;
import com.example.meetup.payments.base.domain.IAggregateStore;



import com.example.meetup.payments.domain.subscriptions.SubscriptionId;
import com.example.meetup.payments.domain.subscriptions.Subscription;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

    
@Component
@RequiredArgsConstructor
public class ExpireSubscriptionsCommandHandler implements IAsyncCommandHandler<ExpireSubscriptionsCommand> {

	private final IAggregateStore aggregateStore;


	@Override
	public void handle(ExpireSubscriptionsCommand command) {

		// TODO: use sql query to get all subscriptions where... and execute command 
		// ExpireSubscriptionCommand for each
		

	}	
    	
}
