package com.example.meetup.meetings.application.meetingCommentingConfigurations;

import com.example.meetup.meetings.base.infrastructure.ICommandDispatcher;
import com.example.meetup.meetings.base.application.IEventHandler;
import com.example.meetup.meetings.base.application.IEvent;

import com.example.meetup.meetings.domain.meeting.events.MeetingCreatedEvent;

import com.example.meetup.meetings.application.meetingCommentingConfigurations.CreateMeetingCommentingConfigurationCommand;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

    
@Component
@RequiredArgsConstructor
public class MeetingCreatedEventListener implements IEventHandler<MeetingCreatedEvent> {

	private final ICommandDispatcher commandDispatcher;


	@Override
	public Class<? extends IEvent> registeredFor() {
		return MeetingCreatedEvent.class;
	}

	@Override
	public void handle(MeetingCreatedEvent event) {

		commandDispatcher.executeCommandAsync(new CreateMeetingCommentingConfigurationCommand(
		                                      	event.getMeetingId()));

	}

}
