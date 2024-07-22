package com.example.meetup.meetings.application.meetingComments;


import org.springframework.stereotype.Component;

import com.example.meetup.meetings.application.meetingComments.view.GetMeetingCommentLikersByIdQuery;
import com.example.meetup.meetings.application.meetingComments.view.MeetingCommentLikersView;
import com.example.meetup.meetings.base.application.IAsyncCommandHandler;
import com.example.meetup.meetings.base.domain.IAggregateStore;
import com.example.meetup.meetings.domain.meetingMemberCommentLike.MeetingMemberCommentLike;
import com.example.meetup.meetings.domain.meetingMemberCommentLike.MeetingMemberCommentLikeId;
import com.example.meetup.meetings.infrastructure.MeetingContext;
import com.example.meetup.meetings.infrastructure.MeetingsQueryDispatcher;

import lombok.RequiredArgsConstructor;

    
@Component
@RequiredArgsConstructor
public class RemoveMeetingCommentLikeCommandHandler implements IAsyncCommandHandler<RemoveMeetingCommentLikeCommand> {

	private final IAggregateStore aggregateStore;

	private final MeetingContext context;
	private final MeetingsQueryDispatcher queryDispatcher;

	@Override
	public void handle(RemoveMeetingCommentLikeCommand command) {

		MeetingCommentLikersView meetingCommentLikersView = queryDispatcher.executeQuery(new GetMeetingCommentLikersByIdQuery(command.getMeetingCommentId()));
    	
		var meetingMemberCommentLikeId = meetingCommentLikersView.getMeetingCommentLikers().stream()
				.filter(mcl -> mcl.getLikerId().equals(context.principalId().getValue()))
				.findAny()
				.get()
				.getMeetingMemberCommentLikeId();
		
		MeetingMemberCommentLike meetingMemberCommentLike = aggregateStore.load(
				new MeetingMemberCommentLikeId(meetingMemberCommentLikeId), 
				MeetingMemberCommentLike.class);

		meetingMemberCommentLike.remove();
		
		aggregateStore.save(meetingMemberCommentLike);

	}	
    	
}
