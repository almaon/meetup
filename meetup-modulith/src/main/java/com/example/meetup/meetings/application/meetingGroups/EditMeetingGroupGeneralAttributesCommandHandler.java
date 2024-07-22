package com.example.meetup.meetings.application.meetingGroups;

import org.springframework.stereotype.Component;

import com.example.meetup.meetings.base.application.IAsyncCommandHandler;
import com.example.meetup.meetings.base.domain.IAggregateStore;
import com.example.meetup.meetings.domain.MeetingGroupLocation;
import com.example.meetup.meetings.domain.meetingGroup.MeetingGroup;
import com.example.meetup.meetings.domain.meetingGroup.MeetingGroupId;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EditMeetingGroupGeneralAttributesCommandHandler
		implements IAsyncCommandHandler<EditMeetingGroupGeneralAttributesCommand> {

	private final IAggregateStore aggregateStore;

	@Override
	public void handle(EditMeetingGroupGeneralAttributesCommand command) {

		MeetingGroup meetingGroup = aggregateStore.load(new MeetingGroupId(command.getMeetingGroupId()),
				MeetingGroup.class);

		meetingGroup.editGeneralAttributes(
				command.getName(), 
				command.getDescription(),
				new MeetingGroupLocation(
						command.getLocationCity(), 
						command.getLocationCountryCode()));

		aggregateStore.save(meetingGroup);

	}

}
