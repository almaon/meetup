package com.example.meetup.payments.application.meetingFees;


import java.util.UUID;

import org.springframework.stereotype.Component;

import com.example.meetup.payments.base.application.ISyncCommandHandler;
import com.example.meetup.payments.base.domain.IAggregateStore;
import com.example.meetup.payments.domain.MeetingId;
import com.example.meetup.payments.domain.MoneyValue;
import com.example.meetup.payments.domain.meetingFees.MeetingFee;
import com.example.meetup.payments.domain.payers.PayerId;

import lombok.RequiredArgsConstructor;

    
@Component
@RequiredArgsConstructor
public class CreateMeetingFeeCommandHandler implements ISyncCommandHandler<CreateMeetingFeeCommand, UUID> {
	
	private final IAggregateStore aggregateStore;


	
	@Override
	public UUID handle(CreateMeetingFeeCommand command) {	

   		var meetingFee = new MeetingFee(
   				new PayerId(command.getPayerId()), 
   				new MeetingId(command.getMeetingId()), 
   				new MoneyValue(command.getValue(), command.getCurrency()));
    	
		
		aggregateStore.save(meetingFee);
		return meetingFee.getMeetingFeeId().getValue();

	}	
    	
}
