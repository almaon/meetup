package com.example.meetup.administration.application.integration.emit;

import com.example.meetup.administration.base.application.IEventHandler;
import com.example.meetup.administration.base.application.IEvent;

import com.example.meetup.meetings.application.integration.listen.meetingGroupProposalRejected.MeetingGroupProposalRejectedMeetingsIntegrationEvent;
import com.example.meetup.administration.domain.meetingGroupProposals.events.MeetingGroupProposalRejectedEvent;

import org.springframework.context.ApplicationEventPublisher;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;


@Component
@RequiredArgsConstructor
public class MeetingGroupProposalRejectedEvent_to_Meetings implements IEventHandler<MeetingGroupProposalRejectedEvent> {


	public final ApplicationEventPublisher applicationEventPublisher;

	@Override
	public Class<? extends IEvent> registeredFor() {
		return MeetingGroupProposalRejectedEvent.class;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void handle(MeetingGroupProposalRejectedEvent event) {

		var integrationEvent = mapToIntegrationEvent(event);
		applicationEventPublisher.publishEvent(integrationEvent);
	}
	
	private com.example.meetup.meetings.application.integration.listen.meetingGroupProposalRejected.MeetingGroupProposalRejectedMeetingsIntegrationEvent mapToIntegrationEvent(com.example.meetup.administration.domain.meetingGroupProposals.events.MeetingGroupProposalRejectedEvent sourceEvent) {
		var targetInstance = new com.example.meetup.meetings.application.integration.listen.meetingGroupProposalRejected.MeetingGroupProposalRejectedMeetingsIntegrationEvent();
		targetInstance.setMeetingGroupProposalId(sourceEvent.getMeetingGroupProposalId());
		targetInstance.setDecisionRejectReason(sourceEvent.getDecisionRejectReason());
		targetInstance.setDecisionDate(sourceEvent.getDecisionDate());
		targetInstance.setDescription(sourceEvent.getDescription());
		targetInstance.setName(sourceEvent.getName());
		targetInstance.setProposalUserId(sourceEvent.getProposalUserId());
		targetInstance.setLocationCity(sourceEvent.getLocationCity());
		targetInstance.setLocationCountryCode(sourceEvent.getLocationCountryCode());
		targetInstance.setProposalDate(sourceEvent.getProposalDate());
		targetInstance.setAdminId(sourceEvent.getAdminId());
		return targetInstance;
	}


}





