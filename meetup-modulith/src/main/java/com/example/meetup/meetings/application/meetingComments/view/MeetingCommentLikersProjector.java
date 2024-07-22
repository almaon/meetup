package com.example.meetup.meetings.application.meetingComments.view;


import java.util.ArrayList;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.meetup.meetings.application.members.view.GetMemberByIdQuery;
import com.example.meetup.meetings.application.members.view.MemberView;
import com.example.meetup.meetings.base.application.IEvent;
import com.example.meetup.meetings.base.application.IProjector;
import com.example.meetup.meetings.domain.meetingComment.events.MeetingCommentAddedEvent;
import com.example.meetup.meetings.domain.meetingComment.events.MeetingCommentRemovedEvent;
import com.example.meetup.meetings.domain.meetingMemberCommentLike.events.MeetingCommentLikedEvent;
import com.example.meetup.meetings.domain.meetingMemberCommentLike.events.MeetingCommentUnlikedEvent;
import com.example.meetup.meetings.infrastructure.MeetingsQueryDispatcher;

import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class MeetingCommentLikersProjector implements IProjector {

	private final MeetingCommentLikersRepository meetingCommentLikersRepository;
	

	private final MeetingsQueryDispatcher queryDispatcher;

	
	private void when(MeetingCommentLikedEvent event) {

		var view = meetingCommentLikersRepository.findByMeetingCommentId(event.getMeetingCommentId());

		MemberView liker = queryDispatcher.executeQuery(new GetMemberByIdQuery(event.getLikerId()));
		
		view.getMeetingCommentLikers().add(
				new MeetingCommentLiker(
						event.getLikerId(), 
						liker.getLogin(), 
						event.getMeetingMemberCommentLikeId()));

	}
	private void when(MeetingCommentRemovedEvent event) {

		var view = meetingCommentLikersRepository.findByMeetingCommentId(event.getMeetingCommentId());
		
		meetingCommentLikersRepository.delete(view);

	}
	private void when(MeetingCommentAddedEvent event) {

		meetingCommentLikersRepository.save(
			new MeetingCommentLikersView(
				event.getMeetingCommentId(),
				new ArrayList<>()));

	}
	private void when(MeetingCommentUnlikedEvent event) {

		var view = meetingCommentLikersRepository.findByMeetingCommentId(event.getMeetingCommentId());
		
		var meetingCommentLiker = view.getMeetingCommentLikers().stream()
				.filter(mcl -> mcl.getLikerId().equals(event.getLikerId()))
				.findAny().get();
		
		view.getMeetingCommentLikers().remove(meetingCommentLiker);

	}
	
	@Override
//	@Transactional//(propagation = Propagation.REQUIRES_NEW)
	public void project(IEvent event) {
		
		if (event instanceof MeetingCommentLikedEvent castedEvent) {
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
		if (event instanceof MeetingCommentUnlikedEvent castedEvent) {
			when(castedEvent);
			return;
		}
			
	}
	
}
