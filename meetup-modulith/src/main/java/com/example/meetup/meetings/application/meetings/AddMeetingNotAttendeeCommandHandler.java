package com.example.meetup.meetings.application.meetings;


import org.springframework.stereotype.Component;

import com.example.meetup.meetings.base.application.IAsyncCommandHandler;
import com.example.meetup.meetings.base.domain.IAggregateStore;
import com.example.meetup.meetings.domain.meeting.Meeting;
import com.example.meetup.meetings.domain.meeting.MeetingId;
import com.example.meetup.meetings.domain.member.MemberId;
import com.example.meetup.meetings.infrastructure.MeetingContext;

import lombok.RequiredArgsConstructor;

    
@Component
@RequiredArgsConstructor
public class AddMeetingNotAttendeeCommandHandler implements IAsyncCommandHandler<AddMeetingNotAttendeeCommand> {

	private final IAggregateStore aggregateStore;

	private final MeetingContext context;

	@Override
	public void handle(AddMeetingNotAttendeeCommand command) {

   		Meeting meeting = aggregateStore.load(new MeetingId(command.getMeetingId()), Meeting.class);
    	
    	meeting.addNotAttendee(context.principalId());
		
		aggregateStore.save(meeting);

	}	
    	
}
