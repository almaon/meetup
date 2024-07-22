package com.example.meetup.meetings.application.meetings;

import com.example.meetup.meetings.base.infrastructure.ICommandDispatcher;
import com.example.meetup.meetings.base.application.IEventHandler;
import com.example.meetup.meetings.base.application.IEvent;

import com.example.meetup.meetings.domain.meeting.events.MeetingAttendeeAddedEvent;

import com.example.meetup.meetings.application.meetings.SendMeetingAttendeeAddedEmailCommand;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

    
@Component
@RequiredArgsConstructor
public class MeetingAttendeeAddedEventListener implements IEventHandler<MeetingAttendeeAddedEvent> {

	private final ICommandDispatcher commandDispatcher;


	@Override
	public Class<? extends IEvent> registeredFor() {
		return MeetingAttendeeAddedEvent.class;
	}

	@Override
	public void handle(MeetingAttendeeAddedEvent event) {

		commandDispatcher.executeCommandAsync(new SendMeetingAttendeeAddedEmailCommand(
		                                      	event.getMeetingId(),
		                                      	event.getAttendeeId()));

	}

}
