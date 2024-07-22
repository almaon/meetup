package com.example.meetup.administration.application.requestMeetingGroupProposalVerification;

import org.springframework.stereotype.Component;

import com.example.meetup.administration.application.integration.listen.meetingGroupProposed.MeetingGroupProposedAdministrationIntegrationEvent;
import com.example.meetup.administration.base.application.IEvent;
import com.example.meetup.administration.base.application.IEventHandler;
import com.example.meetup.administration.base.infrastructure.ICommandDispatcher;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MeetingGroupProposedAdministrationIntegrationEventListener implements IEventHandler<MeetingGroupProposedAdministrationIntegrationEvent> {

	private final ICommandDispatcher commandDispatcher;

	@Override
	public Class<? extends IEvent> registeredFor() {
		return MeetingGroupProposedAdministrationIntegrationEvent.class;
	}

	@Override
	public void handle(MeetingGroupProposedAdministrationIntegrationEvent event) {

		commandDispatcher.executeCommandAsync(
				new RequestMeetingGroupProposalVerificationCommand(
						event.getMeetingGroupProposalId(),
						event.getName(),
						event.getDescription(),
						event.getLocationCity(),
						event.getLocationCountryCode(),
						event.getProposalUserId(),
						event.getProposalDate()));
	}

}
