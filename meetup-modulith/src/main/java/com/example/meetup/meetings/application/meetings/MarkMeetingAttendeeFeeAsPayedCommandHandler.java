package com.example.meetup.meetings.application.meetings;


import org.springframework.stereotype.Component;

import com.example.meetup.meetings.base.application.IAsyncCommandHandler;
import com.example.meetup.meetings.base.domain.IAggregateStore;
import com.example.meetup.meetings.domain.meeting.Meeting;
import com.example.meetup.meetings.domain.meeting.MeetingId;
import com.example.meetup.meetings.domain.member.MemberId;

import lombok.RequiredArgsConstructor;

    
@Component
@RequiredArgsConstructor
public class MarkMeetingAttendeeFeeAsPayedCommandHandler implements IAsyncCommandHandler<MarkMeetingAttendeeFeeAsPayedCommand> {

	private final IAggregateStore aggregateStore;


	@Override
	public void handle(MarkMeetingAttendeeFeeAsPayedCommand command) {

   		Meeting meeting = aggregateStore.load(new MeetingId(command.getMeetingId()), Meeting.class);
    	
    	meeting.markAttendeeFeeAsPayed(new MemberId(command.getMemberId()));
		
		aggregateStore.save(meeting);

	}	
    	
}
