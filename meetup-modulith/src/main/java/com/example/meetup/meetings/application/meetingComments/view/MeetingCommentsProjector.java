package com.example.meetup.meetings.application.meetingComments.view;


import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import com.example.meetup.meetings.base.application.IProjector;
import com.example.meetup.meetings.base.application.IEvent;

import com.example.meetup.meetings.domain.meetingMemberCommentLike.events.MeetingCommentUnlikedEvent;
import com.example.meetup.meetings.domain.meetingComment.events.MeetingCommentEditedEvent;
import com.example.meetup.meetings.domain.meetingMemberCommentLike.events.MeetingCommentLikedEvent;
import com.example.meetup.meetings.domain.meetingComment.events.ReplyToMeetingCommentAddedEvent;
import com.example.meetup.meetings.domain.meetingComment.events.MeetingCommentAddedEvent;
import com.example.meetup.meetings.domain.meetingComment.events.MeetingCommentRemovedEvent;

import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class MeetingCommentsProjector implements IProjector {

	private final MeetingCommentsRepository meetingCommentsRepository;
	


	
	private void when(MeetingCommentLikedEvent event) {

		var view = meetingCommentsRepository.findByMeetingCommentId(event.getMeetingCommentId());

		view.setLikesCount(view.getLikesCount() + 1);

	}
	private void when(ReplyToMeetingCommentAddedEvent event) {

		meetingCommentsRepository.save(
			new MeetingCommentsView(
				event.getMeetingCommentId(),
				event.getInReplyToCommentId(),
				event.getAuthorId(),
				event.getReply(),
				event.getCreateDate(),
				null,
				0));

	}
	private void when(MeetingCommentRemovedEvent event) {

		var view = meetingCommentsRepository.findByMeetingCommentId(event.getMeetingCommentId());
		
		meetingCommentsRepository.delete(view);

	}
	private void when(MeetingCommentAddedEvent event) {

		meetingCommentsRepository.save(
			new MeetingCommentsView(
				event.getMeetingCommentId(),
				null,
				event.getAuthorId(),
				event.getComment(),
				event.getCreateDate(),
				null,
				0));

	}
	private void when(MeetingCommentEditedEvent event) {

		var view = meetingCommentsRepository.findByMeetingCommentId(event.getMeetingCommentId());
		
		view.setComment(event.getEditedComment());
		view.setEditDate(event.getEditDate());

	}
	private void when(MeetingCommentUnlikedEvent event) {

		var view = meetingCommentsRepository.findByMeetingCommentId(event.getMeetingCommentId());
		

	}
	
	@Override
//	@Transactional//(propagation = Propagation.REQUIRES_NEW)
	public void project(IEvent event) {
		
		if (event instanceof MeetingCommentLikedEvent castedEvent) {
			when(castedEvent);
			return;
		}
		if (event instanceof ReplyToMeetingCommentAddedEvent castedEvent) {
			when(castedEvent);
			return;
		}
		if (event instanceof MeetingCommentRemovedEvent castedEvent) {
			when(castedEvent);
			return;
		}
		if (event instanceof MeetingCommentAddedEvent castedEvent) {
			when(castedEvent);
			return;
		}
		if (event instanceof MeetingCommentEditedEvent castedEvent) {
			when(castedEvent);
			return;
		}
		if (event instanceof MeetingCommentUnlikedEvent castedEvent) {
			when(castedEvent);
			return;
		}
			
	}
	
}
