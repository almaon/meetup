package com.example.meetup.meetings.application.meetings;


import org.springframework.stereotype.Component;

import com.example.meetup.meetings.application.meetings.view.GetMeetingDetailsByIdQuery;
import com.example.meetup.meetings.application.meetings.view.MeetingDetailsView;
import com.example.meetup.meetings.base.application.IAsyncCommandHandler;
import com.example.meetup.meetings.base.domain.IAggregateStore;
import com.example.meetup.meetings.base.infrastructure.IQueryDispatcher;

import lombok.RequiredArgsConstructor;

    
@Component
@RequiredArgsConstructor
public class SendMeetingAttendeeAddedEmailCommandHandler implements IAsyncCommandHandler<SendMeetingAttendeeAddedEmailCommand> {

	private final IAggregateStore aggregateStore;
	private final IQueryDispatcher queryDispatcher;


	@Override
	public void handle(SendMeetingAttendeeAddedEmailCommand command) {

		MeetingDetailsView meetingDetails = queryDispatcher.executeQuery(new GetMeetingDetailsByIdQuery(command.getMeetingId()));
		// TODO: send the mail

	}	
    	
}
