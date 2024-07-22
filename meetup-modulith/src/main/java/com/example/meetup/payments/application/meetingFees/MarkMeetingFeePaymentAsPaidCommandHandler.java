package com.example.meetup.payments.application.meetingFees;


import org.springframework.stereotype.Component;

import com.example.meetup.payments.base.application.IAsyncCommandHandler;
import com.example.meetup.payments.base.domain.IAggregateStore;
import com.example.meetup.payments.domain.meetingFeePayments.MeetingFeePayment;
import com.example.meetup.payments.domain.meetingFeePayments.MeetingFeePaymentId;

import lombok.RequiredArgsConstructor;

    
@Component
@RequiredArgsConstructor
public class MarkMeetingFeePaymentAsPaidCommandHandler implements IAsyncCommandHandler<MarkMeetingFeePaymentAsPaidCommand> {

	private final IAggregateStore aggregateStore;


	@Override
	public void handle(MarkMeetingFeePaymentAsPaidCommand command) {

		MeetingFeePayment meetingFeePayment = aggregateStore.load(new MeetingFeePaymentId(command.getMeetingFeePaymentId()), MeetingFeePayment.class);
    	
		meetingFeePayment.markAsPaid();
		
		aggregateStore.save(meetingFeePayment);

	}	
    	
}
