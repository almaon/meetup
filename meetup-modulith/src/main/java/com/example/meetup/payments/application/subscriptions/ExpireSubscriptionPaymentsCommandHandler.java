package com.example.meetup.payments.application.subscriptions;


import com.example.meetup.payments.base.application.IAsyncCommandHandler;
import com.example.meetup.payments.base.domain.IAggregateStore;



import com.example.meetup.payments.domain.subscriptionPayments.SubscriptionPaymentId;
import com.example.meetup.payments.domain.subscriptionPayments.SubscriptionPayment;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

    
@Component
@RequiredArgsConstructor
public class ExpireSubscriptionPaymentsCommandHandler implements IAsyncCommandHandler<ExpireSubscriptionPaymentsCommand> {

	private final IAggregateStore aggregateStore;


	@Override
	public void handle(ExpireSubscriptionPaymentsCommand command) {

		// TODO: use sql query to get all subscriptionpayments where... and execute command 
		// ExpireSubscriptionPaymentCommand for each
		

	}	
    	
}
