package com.example.meetup.meetings.application.meetingComments;


import org.springframework.stereotype.Component;

import com.example.meetup.meetings.application.meetings.view.GetMeetingsByIdQuery;
import com.example.meetup.meetings.application.meetings.view.MeetingsView;
import com.example.meetup.meetings.base.application.IAsyncCommandHandler;
import com.example.meetup.meetings.base.domain.IAggregateStore;
import com.example.meetup.meetings.domain.meetingComment.MeetingComment;
import com.example.meetup.meetings.domain.meetingComment.MeetingCommentId;
import com.example.meetup.meetings.domain.meetingGroup.MeetingGroup;
import com.example.meetup.meetings.domain.meetingGroup.MeetingGroupId;
import com.example.meetup.meetings.infrastructure.MeetingContext;
import com.example.meetup.meetings.infrastructure.MeetingsQueryDispatcher;

import lombok.RequiredArgsConstructor;

    
@Component
@RequiredArgsConstructor
public class RemoveMeetingCommentCommandHandler implements IAsyncCommandHandler<RemoveMeetingCommentCommand> {

	private final IAggregateStore aggregateStore;

	private final MeetingContext context;
	private final MeetingsQueryDispatcher queryDispatcher;

	@Override
	public void handle(RemoveMeetingCommentCommand command) {

		MeetingComment meetingComment = aggregateStore.load(new MeetingCommentId(command.getMeetingCommentId()), MeetingComment.class);
    	
		MeetingsView meetingsView = queryDispatcher.executeQuery(new GetMeetingsByIdQuery(meetingComment.getMeetingId().getValue()));

		MeetingGroup meetingGroup = aggregateStore.load(new MeetingGroupId(meetingsView.getMeetingGroupId()), MeetingGroup.class);
    	
		meetingComment.remove(
				context.principalId(), 
				meetingGroup, 
				command.getReason());
		
		aggregateStore.save(meetingComment);

	}	
    	
}
