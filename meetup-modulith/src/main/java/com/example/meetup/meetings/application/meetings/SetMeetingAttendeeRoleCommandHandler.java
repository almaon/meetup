package com.example.meetup.meetings.application.meetings;


import org.springframework.stereotype.Component;

import com.example.meetup.meetings.base.application.IAsyncCommandHandler;
import com.example.meetup.meetings.base.domain.IAggregateStore;
import com.example.meetup.meetings.domain.meeting.Meeting;
import com.example.meetup.meetings.domain.meeting.MeetingId;
import com.example.meetup.meetings.domain.meetingGroup.MeetingGroup;
import com.example.meetup.meetings.domain.member.MemberId;
import com.example.meetup.meetings.infrastructure.MeetingContext;

import lombok.RequiredArgsConstructor;

    
@Component
@RequiredArgsConstructor
public class SetMeetingAttendeeRoleCommandHandler implements IAsyncCommandHandler<SetMeetingAttendeeRoleCommand> {

	private final IAggregateStore aggregateStore;

	private final MeetingContext context;

	@Override
	public void handle(SetMeetingAttendeeRoleCommand command) {

   		Meeting meeting = aggregateStore.load(new MeetingId(command.getMeetingId()), Meeting.class);
    	
   		MeetingGroup meetingGroup = aggregateStore.load(meeting.getMeetingGroupId(), MeetingGroup.class);

   		meeting.setAttendeeRole(meetingGroup, context.principalId(), new MemberId(command.getMemberId()));
		
		aggregateStore.save(meeting);

	}	
    	
}
