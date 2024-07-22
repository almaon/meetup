package com.example.meetup.payments.application.subscriptions;


import com.example.meetup.payments.base.application.IAsyncCommandHandler;
import com.example.meetup.payments.base.domain.IAggregateStore;





import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

    
@Component
@RequiredArgsConstructor
public class ExecuteSubscriptionPaymentCommandHandler implements IAsyncCommandHandler<ExecuteSubscriptionPaymentCommand> {

	private final IAggregateStore aggregateStore;


	@Override
	public void handle(ExecuteSubscriptionPaymentCommand command) {

    	// TODO: call payment system

	}	
    	
}
