package com.example.meetup.payments.application.subscriptions;


import com.example.meetup.payments.base.application.IAsyncCommandHandler;
import com.example.meetup.payments.base.domain.IAggregateStore;



import com.example.meetup.payments.domain.subscriptionPayments.SubscriptionPaymentId;
import com.example.meetup.payments.domain.subscriptionPayments.SubscriptionPayment;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

    
@Component
@RequiredArgsConstructor
public class MarkSubscriptionPaymentAsPaidCommandHandler implements IAsyncCommandHandler<MarkSubscriptionPaymentAsPaidCommand> {

	private final IAggregateStore aggregateStore;


	@Override
	public void handle(MarkSubscriptionPaymentAsPaidCommand command) {

		SubscriptionPayment subscriptionPayment = aggregateStore.load(new SubscriptionPaymentId(command.getSubscriptionPaymentId()), SubscriptionPayment.class);
    	
		subscriptionPayment.markAsPaid();
		
		aggregateStore.save(subscriptionPayment);

	}	
    	
}
