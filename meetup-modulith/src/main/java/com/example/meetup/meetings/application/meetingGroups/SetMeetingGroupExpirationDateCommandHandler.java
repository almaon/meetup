package com.example.meetup.meetings.application.meetingGroups;

import org.springframework.stereotype.Component;

import com.example.meetup.meetings.base.application.IAsyncCommandHandler;
import com.example.meetup.meetings.base.domain.IAggregateStore;
import com.example.meetup.meetings.domain.meetingGroup.MeetingGroup;
import com.example.meetup.meetings.domain.meetingGroup.MeetingGroupId;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SetMeetingGroupExpirationDateCommandHandler
		implements IAsyncCommandHandler<SetMeetingGroupExpirationDateCommand> {

	private final IAggregateStore aggregateStore;

	@Override
	public void handle(SetMeetingGroupExpirationDateCommand command) {
		MeetingGroup meetingGroup = aggregateStore.load(new MeetingGroupId(command.getMeetingGroupId()),
				MeetingGroup.class);

		meetingGroup.setExpirationDate(command.getDateTo());

		aggregateStore.save(meetingGroup);
	}

}
