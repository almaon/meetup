package com.example.meetup.meetings.application.meetings;


import org.springframework.stereotype.Component;

import com.example.meetup.meetings.base.application.IAsyncCommandHandler;
import com.example.meetup.meetings.base.domain.IAggregateStore;
import com.example.meetup.meetings.domain.meeting.Meeting;
import com.example.meetup.meetings.domain.meeting.MeetingId;
import com.example.meetup.meetings.infrastructure.MeetingContext;

import lombok.RequiredArgsConstructor;

    
@Component
@RequiredArgsConstructor
public class CancelMeetingCommandHandler implements IAsyncCommandHandler<CancelMeetingCommand> {

	private final IAggregateStore aggregateStore;

	private final MeetingContext context;

	@Override
	public void handle(CancelMeetingCommand command) {

   		Meeting meeting = aggregateStore.load(new MeetingId(command.getMeetingId()), Meeting.class);
    	
    	meeting.cancel(context.principalId());
		
		aggregateStore.save(meeting);

	}	
    	
}
