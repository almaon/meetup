package com.example.meetup.meetings.application.meetings;


import org.springframework.stereotype.Component;

import com.example.meetup.meetings.application.meetings.view.GetMeetingsByIdQuery;
import com.example.meetup.meetings.application.meetings.view.MeetingsView;
import com.example.meetup.meetings.base.application.IAsyncCommandHandler;
import com.example.meetup.meetings.base.domain.IAggregateStore;
import com.example.meetup.meetings.base.infrastructure.IQueryDispatcher;
import com.example.meetup.meetings.domain.meeting.Meeting;
import com.example.meetup.meetings.domain.meeting.MeetingId;
import com.example.meetup.meetings.domain.meetingGroup.MeetingGroup;
import com.example.meetup.meetings.domain.meetingGroup.MeetingGroupId;
import com.example.meetup.meetings.infrastructure.MeetingContext;

import lombok.RequiredArgsConstructor;

    
@Component
@RequiredArgsConstructor
public class SignUpMemberToWaitlistCommandHandler implements IAsyncCommandHandler<SignUpMemberToWaitlistCommand> {

	private final IAggregateStore aggregateStore;

	private final IQueryDispatcher queryDispatcher;
	private final MeetingContext context;

	@Override
	public void handle(SignUpMemberToWaitlistCommand command) {

   		Meeting meeting = aggregateStore.load(new MeetingId(command.getMeetingId()), Meeting.class);
    	
		MeetingsView meetings = queryDispatcher.executeQuery(new GetMeetingsByIdQuery(command.getMeetingId()));
		
		MeetingGroup meetingGroup = aggregateStore.load(new MeetingGroupId(meetings.getMeetingGroupId()), MeetingGroup.class);
  	
		meeting.signUpMemberToWaitlist(meetingGroup, context.principalId());
		
		aggregateStore.save(meeting);

	}	
    	
}
