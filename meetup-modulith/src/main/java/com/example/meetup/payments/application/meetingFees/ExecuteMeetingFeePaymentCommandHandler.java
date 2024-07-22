package com.example.meetup.payments.application.meetingFees;


import org.springframework.stereotype.Component;

import com.example.meetup.payments.base.application.IAsyncCommandHandler;
import com.example.meetup.payments.base.domain.IAggregateStore;

import lombok.RequiredArgsConstructor;

    
@Component
@RequiredArgsConstructor
public class ExecuteMeetingFeePaymentCommandHandler implements IAsyncCommandHandler<ExecuteMeetingFeePaymentCommand> {

	private final IAggregateStore aggregateStore;


	@Override
	public void handle(ExecuteMeetingFeePaymentCommand command) {

		// TODO: call payment system

	}	
    	
}
