package com.example.meetup.payments.application.subscriptions;


import org.springframework.stereotype.Component;

import com.example.meetup.payments.base.application.IAsyncCommandHandler;
import com.example.meetup.payments.base.domain.IAggregateStore;
import com.example.meetup.payments.domain.subscriptionRenewalPayments.SubscriptionRenewalPayment;
import com.example.meetup.payments.domain.subscriptionRenewalPayments.SubscriptionRenewalPaymentId;

import lombok.RequiredArgsConstructor;

    
@Component
@RequiredArgsConstructor
public class MarkSubscriptionRenewalPaymentAsPaidCommandHandler implements IAsyncCommandHandler<MarkSubscriptionRenewalPaymentAsPaidCommand> {

	private final IAggregateStore aggregateStore;


	@Override
	public void handle(MarkSubscriptionRenewalPaymentAsPaidCommand command) {

		SubscriptionRenewalPayment subscriptionRenewalPayment = aggregateStore.load(new SubscriptionRenewalPaymentId(command.getSubscriptionRenewalPaymentId()), SubscriptionRenewalPayment.class);
    	
		subscriptionRenewalPayment.markRenewalAsPaid();
		
		aggregateStore.save(subscriptionRenewalPayment);

	}	
    	
}
