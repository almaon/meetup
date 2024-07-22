package com.example.meetup.payments.application.meetingFees;


import java.util.UUID;

import org.springframework.stereotype.Component;

import com.example.meetup.payments.base.application.ISyncCommandHandler;
import com.example.meetup.payments.base.domain.IAggregateStore;
import com.example.meetup.payments.domain.meetingFeePayments.MeetingFeePayment;
import com.example.meetup.payments.domain.meetingFees.MeetingFeeId;

import lombok.RequiredArgsConstructor;

    
@Component
@RequiredArgsConstructor
public class CreateMeetingFeePaymentCommandHandler implements ISyncCommandHandler<CreateMeetingFeePaymentCommand, UUID> {
	
	private final IAggregateStore aggregateStore;


	
	@Override
	public UUID handle(CreateMeetingFeePaymentCommand command) {	

   		var meetingFeePayment = new MeetingFeePayment(
   				new MeetingFeeId(command.getMeetingFeeId()));
    	
		
		aggregateStore.save(meetingFeePayment);
		return null;

	}	
    	
}
