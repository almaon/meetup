package com.example.meetup.meetings.application.meetings.view;


import java.util.UUID;
import java.util.ArrayList;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import com.example.meetup.meetings.base.application.IProjector;
import com.example.meetup.meetings.base.infrastructure.IQueryDispatcher;
import com.example.meetup.meetings.application.members.view.GetMemberByIdQuery;
import com.example.meetup.meetings.application.members.view.MemberView;
import com.example.meetup.meetings.base.application.IEvent;

import com.example.meetup.meetings.domain.meeting.events.MeetingCreatedEvent;
import com.example.meetup.meetings.domain.meeting.events.MeetingAttendeeRemovedEvent;
import com.example.meetup.meetings.domain.meeting.events.MeetingAttendeeAddedEvent;
import com.example.meetup.meetings.domain.meeting.events.MeetingAttendeeAddedEvent;

import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class MeetingAttendeesProjector implements IProjector {

	private final MeetingAttendeesRepository meetingAttendeesRepository;
	

	private final IQueryDispatcher queryDispatcher;

	
	private void when(MeetingAttendeeAddedEvent event) {

		var view = meetingAttendeesRepository.findByMeetingId(event.getMeetingId());
		
		MemberView member = queryDispatcher.executeQuery(new GetMemberByIdQuery(event.getAttendeeId()));
		
		view.getAttendees().add(
				new Attendee(
						member.getMemberId(),
						member.getLogin(),
						member.getLastName(),
						member.getFirstName(),
						member.getEmail(),
						event.getGuestsNumber(),
						false));

	}
	private void when(MeetingCreatedEvent event) {

		meetingAttendeesRepository.save(
			new MeetingAttendeesView(
				event.getMeetingId(),
				new ArrayList<>()));

	}
	private void when(MeetingAttendeeRemovedEvent event) {

		var view = meetingAttendeesRepository.findByMeetingId(event.getMeetingId());
		
		var attendee = view.getAttendees().stream().filter(a -> a.getMemberId().equals(event.getAttendeeId())).findAny().get();
		
		view.getAttendees().remove(attendee);

	}
	
	@Override
//	@Transactional//(propagation = Propagation.REQUIRES_NEW)
	public void project(IEvent event) {
		
		if (event instanceof MeetingAttendeeAddedEvent castedEvent) {
			when(castedEvent);
			return;
		}
		if (event instanceof MeetingCreatedEvent castedEvent) {
			when(castedEvent);
			return;
		}
		if (event instanceof MeetingAttendeeRemovedEvent castedEvent) {
			when(castedEvent);
			return;
		}
			
	}
	
}
