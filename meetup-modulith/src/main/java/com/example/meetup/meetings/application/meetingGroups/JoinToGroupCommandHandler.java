package com.example.meetup.meetings.application.meetingGroups;

import org.springframework.stereotype.Component;

import com.example.meetup.meetings.base.application.IAsyncCommandHandler;
import com.example.meetup.meetings.base.domain.IAggregateStore;
import com.example.meetup.meetings.domain.meetingGroup.MeetingGroup;
import com.example.meetup.meetings.domain.meetingGroup.MeetingGroupId;
import com.example.meetup.meetings.infrastructure.MeetingContext;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JoinToGroupCommandHandler implements IAsyncCommandHandler<JoinToGroupCommand> {

	private final IAggregateStore aggregateStore;

	private final MeetingContext context;

	@Override
	public void handle(JoinToGroupCommand command) {

		MeetingGroup meetingGroup = aggregateStore.load(new MeetingGroupId(command.getMeetingGroupId()),
				MeetingGroup.class);

		meetingGroup.joinToGroupMember(context.principalId());

		aggregateStore.save(meetingGroup);
	}

}
