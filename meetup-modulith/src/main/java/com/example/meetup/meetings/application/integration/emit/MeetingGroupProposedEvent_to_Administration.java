package com.example.meetup.meetings.application.integration.emit;

import com.example.meetup.meetings.base.application.IEventHandler;
import com.example.meetup.meetings.base.application.IEvent;

import com.example.meetup.administration.application.integration.listen.meetingGroupProposed.MeetingGroupProposedAdministrationIntegrationEvent;
import com.example.meetup.meetings.domain.meetingGroupProposal.events.MeetingGroupProposedEvent;

import org.springframework.context.ApplicationEventPublisher;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;


@Component
@RequiredArgsConstructor
public class MeetingGroupProposedEvent_to_Administration implements IEventHandler<MeetingGroupProposedEvent> {


	public final ApplicationEventPublisher applicationEventPublisher;

	@Override
	public Class<? extends IEvent> registeredFor() {
		return MeetingGroupProposedEvent.class;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void handle(MeetingGroupProposedEvent event) {

		var integrationEvent = mapToIntegrationEvent(event);
		applicationEventPublisher.publishEvent(integrationEvent);
	}
	
	private com.example.meetup.administration.application.integration.listen.meetingGroupProposed.MeetingGroupProposedAdministrationIntegrationEvent mapToIntegrationEvent(com.example.meetup.meetings.domain.meetingGroupProposal.events.MeetingGroupProposedEvent sourceEvent) {
		var targetInstance = new com.example.meetup.administration.application.integration.listen.meetingGroupProposed.MeetingGroupProposedAdministrationIntegrationEvent();
		targetInstance.setMeetingGroupProposalId(sourceEvent.getMeetingGroupProposalId());
		targetInstance.setDescription(sourceEvent.getDescription());
		targetInstance.setLocationCity(sourceEvent.getLocationCity());
		targetInstance.setName(sourceEvent.getName());
		targetInstance.setLocationCountryCode(sourceEvent.getLocationCountryCode());
		targetInstance.setProposalUserId(sourceEvent.getProposalUserId());
		targetInstance.setProposalDate(sourceEvent.getProposalDate());
		return targetInstance;
	}


}





