package com.example.meetup.payments.application.subscriptions;


import java.util.UUID;

import org.springframework.stereotype.Component;

import com.example.meetup.payments.base.application.ISyncCommandHandler;
import com.example.meetup.payments.base.domain.IAggregateStore;
import com.example.meetup.payments.domain.subscriptionPayments.SubscriptionPayment;
import com.example.meetup.payments.domain.subscriptionPayments.SubscriptionPaymentId;
import com.example.meetup.payments.domain.subscriptions.Subscription;

import lombok.RequiredArgsConstructor;

    
@Component
@RequiredArgsConstructor
public class CreateSubscriptionCommandHandler implements ISyncCommandHandler<CreateSubscriptionCommand, UUID> {
	
	private final IAggregateStore aggregateStore;


	
	@Override
	public UUID handle(CreateSubscriptionCommand command) {	

		SubscriptionPayment subscriptionPayment = aggregateStore.load(new SubscriptionPaymentId(command.getSubscriptionPaymentId()), SubscriptionPayment.class);
    	
		var subscription = new Subscription(subscriptionPayment.getSnapshot());
		
		aggregateStore.save(subscription);
		return null;

	}	
    	
}
