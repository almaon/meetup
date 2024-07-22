package com.example.meetup.meetings.application.meetingGroups;

import org.springframework.stereotype.Component;

import com.example.meetup.meetings.base.application.IEvent;
import com.example.meetup.meetings.base.application.IEventHandler;
import com.example.meetup.meetings.base.infrastructure.ICommandDispatcher;
import com.example.meetup.meetings.domain.meetingGroupProposal.events.MeetingGroupProposalAcceptedEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MeetingGroupProposalAcceptedEventListener implements IEventHandler<MeetingGroupProposalAcceptedEvent> {

	private final ICommandDispatcher commandDispatcher;

	@Override
	public Class<? extends IEvent> registeredFor() {
		return MeetingGroupProposalAcceptedEvent.class;
	}

	@Override
	public void handle(MeetingGroupProposalAcceptedEvent event) {

		commandDispatcher.executeCommandAsync(new CreateNewMeetingGroupCommand(event.getMeetingGroupProposalId()));
	}

}
