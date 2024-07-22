package com.example.meetup.payments.application.meetingFees;


import org.springframework.stereotype.Component;

import com.example.meetup.payments.base.application.IAsyncCommandHandler;
import com.example.meetup.payments.base.domain.IAggregateStore;
import com.example.meetup.payments.domain.meetingFees.MeetingFee;
import com.example.meetup.payments.domain.meetingFees.MeetingFeeId;

import lombok.RequiredArgsConstructor;

    
@Component
@RequiredArgsConstructor
public class MarkMeetingFeeAsPaidCommandHandler implements IAsyncCommandHandler<MarkMeetingFeeAsPaidCommand> {

	private final IAggregateStore aggregateStore;


	@Override
	public void handle(MarkMeetingFeeAsPaidCommand command) {

		MeetingFee meetingFee = aggregateStore.load(new MeetingFeeId(command.getMeetingFeeId()), MeetingFee.class);
    	
		meetingFee.markAsPaid();
		
		aggregateStore.save(meetingFee);

	}	
    	
}
