package com.example.meetup.meetings.application.meetings;


import org.springframework.stereotype.Component;

import com.example.meetup.meetings.application.meetings.view.GetMemberMeetingsByIdQuery;
import com.example.meetup.meetings.application.meetings.view.MemberMeetingsView;
import com.example.meetup.meetings.base.application.IAsyncCommandHandler;
import com.example.meetup.meetings.base.domain.IAggregateStore;
import com.example.meetup.meetings.base.infrastructure.IQueryDispatcher;
import com.example.meetup.meetings.domain.meeting.Meeting;
import com.example.meetup.meetings.domain.meeting.MeetingId;
import com.example.meetup.meetings.domain.member.MemberId;
import com.example.meetup.meetings.infrastructure.MeetingContext;

import lombok.RequiredArgsConstructor;

    
@Component
@RequiredArgsConstructor
public class RemoveMeetingAttendeeCommandHandler implements IAsyncCommandHandler<RemoveMeetingAttendeeCommand> {

	private final IAggregateStore aggregateStore;
	private final IQueryDispatcher queryDispatcher;

	private final MeetingContext context;

	@Override
	public void handle(RemoveMeetingAttendeeCommand command) {

   		Meeting meeting = aggregateStore.load(new MeetingId(command.getMeetingId()), Meeting.class);
    	
		MemberMeetingsView memberMeetings = queryDispatcher.executeQuery(new GetMemberMeetingsByIdQuery(command.getAttendeeId()));
		com.example.meetup.meetings.application.meetings.view.Meeting meetingsView = memberMeetings.getMeetings().stream().
				filter(m -> m.getMeetingId().equals(command.getMeetingId())).findAny().get();
    	
		meeting.removeAttendee(
    			new MemberId(command.getAttendeeId()), 
    			context.principalId(),
    			command.getRemovingReason(),
    			meetingsView.getGuestsNumber());
    	
		
		aggregateStore.save(meeting);

	}	
    	
}
