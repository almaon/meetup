package com.example.meetup.meetings.application.meetingGroups;

import com.example.meetup.meetings.base.application.IAsyncCommandHandler;
import com.example.meetup.meetings.base.domain.IAggregateStore;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SendMeetingGroupCreatedEmailCommandHandler
		implements IAsyncCommandHandler<SendMeetingGroupCreatedEmailCommand> {

	private final IAggregateStore aggregateStore;

	@Override
	public void handle(SendMeetingGroupCreatedEmailCommand command) {

		// TODO: send the mail

	}

}
