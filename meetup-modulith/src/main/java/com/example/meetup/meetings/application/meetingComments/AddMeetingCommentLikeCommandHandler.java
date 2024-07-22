package com.example.meetup.meetings.application.meetingComments;


import org.springframework.stereotype.Component;

import com.example.meetup.meetings.application.meetingComments.view.GetMeetingCommentLikersByIdQuery;
import com.example.meetup.meetings.application.meetingComments.view.MeetingCommentLikersView;
import com.example.meetup.meetings.application.meetings.view.GetMeetingsByIdQuery;
import com.example.meetup.meetings.application.meetings.view.MeetingsView;
import com.example.meetup.meetings.base.application.IAsyncCommandHandler;
import com.example.meetup.meetings.base.domain.IAggregateStore;
import com.example.meetup.meetings.domain.meetingComment.MeetingComment;
import com.example.meetup.meetings.domain.meetingComment.MeetingCommentId;
import com.example.meetup.meetings.domain.meetingGroup.MeetingGroupId;
import com.example.meetup.meetings.domain.member.MeetingGroupMemberData;
import com.example.meetup.meetings.infrastructure.MeetingContext;
import com.example.meetup.meetings.infrastructure.MeetingsQueryDispatcher;

import lombok.RequiredArgsConstructor;

    
@Component
@RequiredArgsConstructor
public class AddMeetingCommentLikeCommandHandler implements IAsyncCommandHandler<AddMeetingCommentLikeCommand> {

	private final IAggregateStore aggregateStore;

	private final MeetingContext context;
	private final MeetingsQueryDispatcher queryDispatcher;

	@Override
	public void handle(AddMeetingCommentLikeCommand command) {

        MeetingComment meetingComment = aggregateStore.load(new MeetingCommentId(command.getMeetingCommentId()), MeetingComment.class);

		MeetingsView meetingsView = queryDispatcher.executeQuery(new GetMeetingsByIdQuery(meetingComment.getMeetingId().getValue()));

		var likerMeetingGroupMemberData = new MeetingGroupMemberData(
				context.principalId(),
				new MeetingGroupId(meetingsView.getMeetingGroupId()));
		
		MeetingCommentLikersView meetingCommentLikersView = queryDispatcher.executeQuery(new GetMeetingCommentLikersByIdQuery(command.getMeetingCommentId()));

		var meetingMemeberCommentLikesCount = (int) meetingCommentLikersView.getMeetingCommentLikers().stream()
			.filter(mcl -> mcl.getLikerId().equals(context.principalId().getValue())).count();
        
   		var meetingMemberCommentLike = meetingComment.like(
   				context.principalId(), 
   				likerMeetingGroupMemberData, 
   				meetingMemeberCommentLikesCount);
    	
		aggregateStore.save(meetingMemberCommentLike);

	}	
    	
}
