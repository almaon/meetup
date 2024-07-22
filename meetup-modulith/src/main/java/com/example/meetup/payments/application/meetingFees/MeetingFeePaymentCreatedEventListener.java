package com.example.meetup.payments.application.meetingFees;
import org.springframework.stereotype.Component;

import com.example.meetup.payments.base.application.IEvent;
import com.example.meetup.payments.base.application.IEventHandler;
import com.example.meetup.payments.base.infrastructure.ICommandDispatcher;
import com.example.meetup.payments.domain.meetingFeePayments.events.MeetingFeePaymentCreatedEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MeetingFeePaymentCreatedEventListener implements IEventHandler<MeetingFeePaymentCreatedEvent> {

	private final ICommandDispatcher commandDispatcher;

	@Override
	public Class<? extends IEvent> registeredFor() {
		return MeetingFeePaymentCreatedEvent.class;
	}

	@Override
	public void handle(MeetingFeePaymentCreatedEvent event) {
		commandDispatcher.executeCommandAsync(new ExecuteMeetingFeePaymentCommand());
	}

}
