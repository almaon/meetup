package com.example.meetup.meetings.application.meetings.view;


import java.util.ArrayList;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.meetup.meetings.application.members.view.GetMemberByIdQuery;
import com.example.meetup.meetings.application.members.view.MemberView;
import com.example.meetup.meetings.base.application.IEvent;
import com.example.meetup.meetings.base.application.IProjector;
import com.example.meetup.meetings.base.infrastructure.IQueryDispatcher;
import com.example.meetup.meetings.domain.meeting.events.MeetingCreatedEvent;
import com.example.meetup.meetings.domain.meeting.events.MeetingWaitlistMemberAddedEvent;
import com.example.meetup.meetings.domain.meeting.events.MemberSignedOffFromMeetingWaitlistEvent;
import com.example.meetup.meetings.domain.meeting.events.WaitinglistMemberMovedToAttendeesEvent;

import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class MeetingWaitlistsProjector implements IProjector {

	private final MeetingWaitlistsRepository meetingWaitlistsRepository;
	

	private final IQueryDispatcher queryDispatcher;

	
	private void when(MemberSignedOffFromMeetingWaitlistEvent event) {

		var view = meetingWaitlistsRepository.findByMeetingId(event.getMeetingId());
		
		var waitlistMember = view.getMembersInWaitList().stream().filter(m -> m.getMemberId().equals(event.getMemberId())).findAny().get();
		
		waitlistMember.setSignOffDate(event.getSignOffDate());
		waitlistMember.setIsSignedOff(true);

	}
	private void when(WaitinglistMemberMovedToAttendeesEvent event) {

		var view = meetingWaitlistsRepository.findByMeetingId(event.getMeetingId());
		
		var memberOnWaitlist = view.getMembersInWaitList().stream().filter(m -> m.getMemberId().equals(event.getMemberId())).findAny().get();
		
		memberOnWaitlist.setIsMovedToAttendee(true);
		memberOnWaitlist.setMovedToAttendeesDate(event.getMovedToAttendeesDate());

	}
	private void when(MeetingWaitlistMemberAddedEvent event) {

		var view = meetingWaitlistsRepository.findByMeetingId(event.getMeetingId());
		
		MemberView member = queryDispatcher.executeQuery(new GetMemberByIdQuery(event.getMemberId()));
		
		view.getMembersInWaitList().add(
				new MemberInWaitList(
						event.getMemberId(), 
						member.getLogin(), 
						view.getMembersInWaitList().size(), 
						event.getSignUpDate(), 
						null, 
						null, 
						false, 
						false));
		

	}
	private void when(MeetingCreatedEvent event) {

		meetingWaitlistsRepository.save(
			new MeetingWaitlistsView(
					event.getMeetingId(), 
					new ArrayList<>()));

	}
	
	@Override
//	@Transactional//(propagation = Propagation.REQUIRES_NEW)
	public void project(IEvent event) {
		
		if (event instanceof MemberSignedOffFromMeetingWaitlistEvent castedEvent) {
			when(castedEvent);
			return;
		}
		if (event instanceof WaitinglistMemberMovedToAttendeesEvent castedEvent) {
			when(castedEvent);
			return;
		}
		if (event instanceof MeetingWaitlistMemberAddedEvent castedEvent) {
			when(castedEvent);
			return;
		}
		if (event instanceof MeetingCreatedEvent castedEvent) {
			when(castedEvent);
			return;
		}
			
	}
	
}
