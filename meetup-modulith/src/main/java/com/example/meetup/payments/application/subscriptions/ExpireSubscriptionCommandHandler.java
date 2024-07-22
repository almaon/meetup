package com.example.meetup.payments.application.subscriptions;


import org.springframework.stereotype.Component;

import com.example.meetup.payments.base.application.IAsyncCommandHandler;
import com.example.meetup.payments.base.domain.IAggregateStore;
import com.example.meetup.payments.domain.subscriptions.Subscription;
import com.example.meetup.payments.domain.subscriptions.SubscriptionId;

import lombok.RequiredArgsConstructor;

    
@Component
@RequiredArgsConstructor
public class ExpireSubscriptionCommandHandler implements IAsyncCommandHandler<ExpireSubscriptionCommand> {

	private final IAggregateStore aggregateStore;


	@Override
	public void handle(ExpireSubscriptionCommand command) {

		Subscription subscription = aggregateStore.load(new SubscriptionId(command.getSubscriptionId()), Subscription.class);
    	
		subscription.expire();
		
		aggregateStore.save(subscription);

	}	
    	
}
