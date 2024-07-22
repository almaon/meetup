package com.example.meetup.payments.application.subscriptions;


import org.springframework.stereotype.Component;

import com.example.meetup.payments.base.application.IAsyncCommandHandler;
import com.example.meetup.payments.base.domain.IAggregateStore;
import com.example.meetup.payments.domain.subscriptionRenewalPayments.SubscriptionRenewalPayment;
import com.example.meetup.payments.domain.subscriptionRenewalPayments.SubscriptionRenewalPaymentId;
import com.example.meetup.payments.domain.subscriptions.Subscription;
import com.example.meetup.payments.domain.subscriptions.SubscriptionId;

import lombok.RequiredArgsConstructor;

    
@Component
@RequiredArgsConstructor
public class RenewSubscriptionCommandHandler implements IAsyncCommandHandler<RenewSubscriptionCommand> {

	private final IAggregateStore aggregateStore;


	@Override
	public void handle(RenewSubscriptionCommand command) {

		SubscriptionRenewalPayment subscriptionRenewalPayment = aggregateStore.load(
				new SubscriptionRenewalPaymentId(command.getSubscriptionRenewalPaymentId()), SubscriptionRenewalPayment.class);
    	
		Subscription subscription = aggregateStore.load(new SubscriptionId(command.getSubscriptionId()), Subscription.class);

		subscription.renew(subscriptionRenewalPayment.getSnapshot());
		
		aggregateStore.save(subscription);

	}	
    	
}
