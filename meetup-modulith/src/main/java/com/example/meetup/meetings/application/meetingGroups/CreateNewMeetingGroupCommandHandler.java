package com.example.meetup.meetings.application.meetingGroups;

import com.example.meetup.meetings.base.application.IAsyncCommandHandler;
import com.example.meetup.meetings.base.domain.IAggregateStore;

import com.example.meetup.meetings.domain.meetingGroup.MeetingGroupId;
import com.example.meetup.meetings.domain.meetingGroup.MeetingGroup;
import com.example.meetup.meetings.domain.meetingGroupProposal.MeetingGroupProposal;
import com.example.meetup.meetings.domain.meetingGroupProposal.MeetingGroupProposalId;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateNewMeetingGroupCommandHandler implements IAsyncCommandHandler<CreateNewMeetingGroupCommand> {

	private final IAggregateStore aggregateStore;

	@Override
	public void handle(CreateNewMeetingGroupCommand command) {

		MeetingGroupProposal meetingGroupProposal = aggregateStore
				.load(new MeetingGroupProposalId(command.getMeetingGroupProposalId()), MeetingGroupProposal.class);

		var meetingGroup = meetingGroupProposal.createMeetingGroup();

		aggregateStore.save(meetingGroup);

	}

}
