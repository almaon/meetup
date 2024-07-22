package com.example.meetup.payments.application.subscriptions;


import org.springframework.stereotype.Component;

import com.example.meetup.payments.base.application.IAsyncCommandHandler;
import com.example.meetup.payments.base.domain.IAggregateStore;
import com.example.meetup.payments.domain.subscriptionPayments.SubscriptionPayment;
import com.example.meetup.payments.domain.subscriptionPayments.SubscriptionPaymentId;

import lombok.RequiredArgsConstructor;

    
@Component
@RequiredArgsConstructor
public class ExpireSubscriptionPaymentCommandHandler implements IAsyncCommandHandler<ExpireSubscriptionPaymentCommand> {

	private final IAggregateStore aggregateStore;


	@Override
	public void handle(ExpireSubscriptionPaymentCommand command) {

		SubscriptionPayment subscriptionPayment = aggregateStore.load(new SubscriptionPaymentId(command.getPaymentId()), SubscriptionPayment.class);
    	
		subscriptionPayment.expire();
		
		aggregateStore.save(subscriptionPayment);

	}	
    	
}
