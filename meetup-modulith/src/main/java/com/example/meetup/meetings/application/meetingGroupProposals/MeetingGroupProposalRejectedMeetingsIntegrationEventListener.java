
package com.example.meetup.meetings.application.meetingGroupProposals;

import com.example.meetup.meetings.base.infrastructure.ICommandDispatcher;
import com.example.meetup.meetings.base.application.IEventHandler;
import com.example.meetup.meetings.base.application.IEvent;
import com.example.meetup.meetings.application.integration.listen.meetingGroupProposalRejected.MeetingGroupProposalRejectedMeetingsIntegrationEvent;

import com.example.meetup.meetings.application.meetingGroupProposals.RejectMeetingGroupProposalCommand;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

    
@Component
@RequiredArgsConstructor
public class MeetingGroupProposalRejectedMeetingsIntegrationEventListener implements IEventHandler<MeetingGroupProposalRejectedMeetingsIntegrationEvent> {

	private final ICommandDispatcher commandDispatcher;


	@Override
	public Class<? extends IEvent> registeredFor() {
		return MeetingGroupProposalRejectedMeetingsIntegrationEvent.class;
	}

	@Override
	public void handle(MeetingGroupProposalRejectedMeetingsIntegrationEvent event) {

		commandDispatcher.executeCommandAsync(
			new RejectMeetingGroupProposalCommand(
				event.getMeetingGroupProposalId(),
				event.getDecisionRejectReason()));

	}

}
