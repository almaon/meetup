package com.example.meetup.payments.application.meetingFees;

import org.springframework.stereotype.Component;

import com.example.meetup.payments.base.application.IEvent;
import com.example.meetup.payments.base.application.IEventHandler;
import com.example.meetup.payments.base.domain.IAggregateStore;
import com.example.meetup.payments.base.infrastructure.ICommandDispatcher;
import com.example.meetup.payments.domain.meetingFeePayments.MeetingFeePayment;
import com.example.meetup.payments.domain.meetingFeePayments.MeetingFeePaymentId;
import com.example.meetup.payments.domain.meetingFeePayments.events.MeetingFeePaymentPaidEvent;

import lombok.RequiredArgsConstructor;

    
@Component
@RequiredArgsConstructor
public class MeetingFeePaymentPaidEventListener implements IEventHandler<MeetingFeePaymentPaidEvent> {

	private final ICommandDispatcher commandDispatcher;

	private final IAggregateStore aggregateStore;

	@Override
	public Class<? extends IEvent> registeredFor() {
		return MeetingFeePaymentPaidEvent.class;
	}

	@Override
	public void handle(MeetingFeePaymentPaidEvent event) {

		MeetingFeePayment meetingFeePayment = aggregateStore.load(
				new MeetingFeePaymentId(event.getMeetingFeePaymentId()), MeetingFeePayment.class);
				
		commandDispatcher.executeCommandAsync(
				new MarkMeetingFeeAsPaidCommand(meetingFeePayment.getMeetingFeeId().getValue()));

	}

}
