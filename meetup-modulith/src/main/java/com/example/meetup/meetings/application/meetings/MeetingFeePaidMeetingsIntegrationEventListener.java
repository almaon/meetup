
package com.example.meetup.meetings.application.meetings;

import com.example.meetup.meetings.base.infrastructure.ICommandDispatcher;
import com.example.meetup.meetings.base.application.IEventHandler;
import com.example.meetup.meetings.base.application.IEvent;
import com.example.meetup.meetings.application.integration.listen.meetingFeePaid.MeetingFeePaidMeetingsIntegrationEvent;

import com.example.meetup.meetings.application.meetings.MarkMeetingAttendeeFeeAsPayedCommand;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

    
@Component
@RequiredArgsConstructor
public class MeetingFeePaidMeetingsIntegrationEventListener implements IEventHandler<MeetingFeePaidMeetingsIntegrationEvent> {

	private final ICommandDispatcher commandDispatcher;


	@Override
	public Class<? extends IEvent> registeredFor() {
		return MeetingFeePaidMeetingsIntegrationEvent.class;
	}

	@Override
	public void handle(MeetingFeePaidMeetingsIntegrationEvent event) {

		commandDispatcher.executeCommandAsync(
			new MarkMeetingAttendeeFeeAsPayedCommand(
				null,
				null));

	}

}
