package com.example.meetup.administration.application.integration.emit;

import com.example.meetup.administration.base.application.IEventHandler;
import com.example.meetup.administration.base.application.IEvent;

import com.example.meetup.meetings.application.integration.listen.meetingGroupProposalAccepted.MeetingGroupProposalAcceptedMeetingsIntegrationEvent;
import com.example.meetup.administration.domain.meetingGroupProposals.events.MeetingGroupProposalAcceptedEvent;

import org.springframework.context.ApplicationEventPublisher;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;


@Component
@RequiredArgsConstructor
public class MeetingGroupProposalAcceptedEvent_to_Meetings implements IEventHandler<MeetingGroupProposalAcceptedEvent> {


	public final ApplicationEventPublisher applicationEventPublisher;

	@Override
	public Class<? extends IEvent> registeredFor() {
		return MeetingGroupProposalAcceptedEvent.class;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void handle(MeetingGroupProposalAcceptedEvent event) {

		var integrationEvent = mapToIntegrationEvent(event);
		applicationEventPublisher.publishEvent(integrationEvent);
	}
	
	private com.example.meetup.meetings.application.integration.listen.meetingGroupProposalAccepted.MeetingGroupProposalAcceptedMeetingsIntegrationEvent mapToIntegrationEvent(com.example.meetup.administration.domain.meetingGroupProposals.events.MeetingGroupProposalAcceptedEvent sourceEvent) {
		var targetInstance = new com.example.meetup.meetings.application.integration.listen.meetingGroupProposalAccepted.MeetingGroupProposalAcceptedMeetingsIntegrationEvent();
		targetInstance.setMeetingGroupProposalId(sourceEvent.getMeetingGroupProposalId());
		targetInstance.setDescription(sourceEvent.getDescription());
		targetInstance.setName(sourceEvent.getName());
		targetInstance.setProposalUserId(sourceEvent.getProposalUserId());
		targetInstance.setLocationCity(sourceEvent.getLocationCity());
		targetInstance.setLocationCountryCode(sourceEvent.getLocationCountryCode());
		targetInstance.setProposalDate(sourceEvent.getProposalDate());
		targetInstance.setDecisionDate(sourceEvent.getDecisionDate());
		targetInstance.setAdminId(sourceEvent.getAdminId());
		return targetInstance;
	}


}





