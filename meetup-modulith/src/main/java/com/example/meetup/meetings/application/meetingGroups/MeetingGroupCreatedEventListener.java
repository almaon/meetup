package com.example.meetup.meetings.application.meetingGroups;

import com.example.meetup.meetings.base.infrastructure.ICommandDispatcher;
import com.example.meetup.meetings.base.application.IEventHandler;
import com.example.meetup.meetings.base.application.IEvent;

import com.example.meetup.meetings.domain.meetingGroup.events.MeetingGroupCreatedEvent;

import com.example.meetup.meetings.application.meetingGroups.SendMeetingGroupCreatedEmailCommand;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MeetingGroupCreatedEventListener implements IEventHandler<MeetingGroupCreatedEvent> {

	private final ICommandDispatcher commandDispatcher;

	@Override
	public Class<? extends IEvent> registeredFor() {
		return MeetingGroupCreatedEvent.class;
	}

	@Override
	public void handle(MeetingGroupCreatedEvent event) {

		// TODO: transactional error
//		commandDispatcher.executeCommandAsync(
//				new SendMeetingGroupCreatedEmailCommand(
//						event.getMeetingGroupId(), 
//						event.getCreatorId()));

	}

}
