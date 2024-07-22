
package com.example.meetup.meetings.application.meetingGroupProposals;

import com.example.meetup.meetings.base.infrastructure.ICommandDispatcher;
import com.example.meetup.meetings.base.application.IEventHandler;
import com.example.meetup.meetings.base.application.IEvent;
import com.example.meetup.meetings.application.integration.listen.meetingGroupProposalAccepted.MeetingGroupProposalAcceptedMeetingsIntegrationEvent;

import com.example.meetup.meetings.application.meetingGroupProposals.AcceptMeetingGroupProposalCommand;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

    
@Component
@RequiredArgsConstructor
public class MeetingGroupProposalAcceptedMeetingsIntegrationEventListener implements IEventHandler<MeetingGroupProposalAcceptedMeetingsIntegrationEvent> {

	private final ICommandDispatcher commandDispatcher;


	@Override
	public Class<? extends IEvent> registeredFor() {
		return MeetingGroupProposalAcceptedMeetingsIntegrationEvent.class;
	}

	@Override
	public void handle(MeetingGroupProposalAcceptedMeetingsIntegrationEvent event) {

		commandDispatcher.executeCommandAsync(
			new AcceptMeetingGroupProposalCommand(
				event.getMeetingGroupProposalId()));

	}

}
