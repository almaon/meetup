package com.example.meetup.meetings.application.integration.emit;

import com.example.meetup.meetings.base.application.IEventHandler;
import com.example.meetup.meetings.base.application.IEvent;

import com.example.meetup.payments.application.integration.listen.meetingAttendeeAdded.MeetingAttendeeAddedPaymentsIntegrationEvent;
import com.example.meetup.meetings.domain.meeting.events.MeetingAttendeeAddedEvent;

import org.springframework.context.ApplicationEventPublisher;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;


@Component
@RequiredArgsConstructor
public class MeetingAttendeeAddedEvent_to_Payments implements IEventHandler<MeetingAttendeeAddedEvent> {


	public final ApplicationEventPublisher applicationEventPublisher;

	@Override
	public Class<? extends IEvent> registeredFor() {
		return MeetingAttendeeAddedEvent.class;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void handle(MeetingAttendeeAddedEvent event) {

		var integrationEvent = mapToIntegrationEvent(event);
		applicationEventPublisher.publishEvent(integrationEvent);
	}
	
	private com.example.meetup.payments.application.integration.listen.meetingAttendeeAdded.MeetingAttendeeAddedPaymentsIntegrationEvent mapToIntegrationEvent(com.example.meetup.meetings.domain.meeting.events.MeetingAttendeeAddedEvent sourceEvent) {
		var targetInstance = new com.example.meetup.payments.application.integration.listen.meetingAttendeeAdded.MeetingAttendeeAddedPaymentsIntegrationEvent();
		targetInstance.setMeetingId(sourceEvent.getMeetingId());
		targetInstance.setAttendeeId(sourceEvent.getAttendeeId());
		targetInstance.setRsvpDate(sourceEvent.getRsvpDate());
		targetInstance.setRole(sourceEvent.getRole());
		targetInstance.setGuestsNumber(sourceEvent.getGuestsNumber());
		targetInstance.setFeeValue(sourceEvent.getFeeValue());
		targetInstance.setFeeCurrency(sourceEvent.getFeeCurrency());
		return targetInstance;
	}


}





