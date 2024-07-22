package com.example.meetup.payments.application.integration.emit;

import com.example.meetup.payments.base.application.IEventHandler;
import com.example.meetup.payments.base.application.IEvent;

import com.example.meetup.meetings.application.integration.listen.meetingFeePaid.MeetingFeePaidMeetingsIntegrationEvent;
import com.example.meetup.payments.domain.meetingFees.events.MeetingFeePaidEvent;

import org.springframework.context.ApplicationEventPublisher;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;


@Component
@RequiredArgsConstructor
public class MeetingFeePaidEvent_to_Meetings implements IEventHandler<MeetingFeePaidEvent> {


	public final ApplicationEventPublisher applicationEventPublisher;

	@Override
	public Class<? extends IEvent> registeredFor() {
		return MeetingFeePaidEvent.class;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void handle(MeetingFeePaidEvent event) {

		var integrationEvent = mapToIntegrationEvent(event);
		applicationEventPublisher.publishEvent(integrationEvent);
	}
	
	private com.example.meetup.meetings.application.integration.listen.meetingFeePaid.MeetingFeePaidMeetingsIntegrationEvent mapToIntegrationEvent(com.example.meetup.payments.domain.meetingFees.events.MeetingFeePaidEvent sourceEvent) {
		var targetInstance = new com.example.meetup.meetings.application.integration.listen.meetingFeePaid.MeetingFeePaidMeetingsIntegrationEvent();
		targetInstance.setMeetingFeeId(sourceEvent.getMeetingFeeId());
		targetInstance.setStatus(sourceEvent.getStatus());
		return targetInstance;
	}


}





