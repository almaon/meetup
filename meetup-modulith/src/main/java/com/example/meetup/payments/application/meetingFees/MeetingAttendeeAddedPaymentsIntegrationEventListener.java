package com.example.meetup.payments.application.meetingFees;


import org.springframework.stereotype.Component;

import com.example.meetup.payments.application.integration.listen.meetingAttendeeAdded.MeetingAttendeeAddedPaymentsIntegrationEvent;
import com.example.meetup.payments.base.application.IEvent;
import com.example.meetup.payments.base.application.IEventHandler;
import com.example.meetup.payments.base.infrastructure.ICommandDispatcher;

import lombok.RequiredArgsConstructor;

    
@Component
@RequiredArgsConstructor
public class MeetingAttendeeAddedPaymentsIntegrationEventListener implements IEventHandler<MeetingAttendeeAddedPaymentsIntegrationEvent> {

	private final ICommandDispatcher commandDispatcher;


	@Override
	public Class<? extends IEvent> registeredFor() {
		return MeetingAttendeeAddedPaymentsIntegrationEvent.class;
	}

	@Override
	public void handle(MeetingAttendeeAddedPaymentsIntegrationEvent event) {

		commandDispatcher.executeCommandSync(
			new CreateMeetingFeeCommand(
				event.getFeeValue(),
				event.getMeetingId(),
				event.getAttendeeId(),
				event.getFeeCurrency()));

	}

}
