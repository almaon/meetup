package com.example.meetup.meetings.application.meetings.view;


import java.util.ArrayList;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.meetup.meetings.base.application.IEvent;
import com.example.meetup.meetings.base.application.IProjector;
import com.example.meetup.meetings.base.infrastructure.IQueryDispatcher;
import com.example.meetup.meetings.domain.meeting.events.MeetingAttendeeAddedEvent;
import com.example.meetup.meetings.domain.meeting.events.MeetingAttendeeRemovedEvent;
import com.example.meetup.meetings.domain.meeting.events.MeetingCanceledEvent;
import com.example.meetup.meetings.domain.meeting.events.MeetingMainAttributesChangedEvent;
import com.example.meetup.meetings.domain.member.events.MemberCreatedEvent;

import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class MemberMeetingsProjector implements IProjector {

	private final MemberMeetingsRepository memberMeetingsRepository;
	

	private final IQueryDispatcher queryDispatcher;

	
	private void when(MeetingAttendeeAddedEvent event) {

		var view = memberMeetingsRepository.findByMemberId(event.getAttendeeId());
		
		MeetingsView meeting = queryDispatcher.executeQuery(new GetMeetingsByIdQuery(event.getMeetingId()));
		
		view.getMeetings().add(
				new Meeting(
						event.getMeetingId(), 
						meeting.getTermEndDate(), 
						meeting.getTitle(), 
						meeting.getMeetingLocationPostalCode(), 
						meeting.getTermStartDate(), 
						meeting.getMeetingLocationAddress(), 
						meeting.getDescription(), 
						meeting.getMeetingLocationCity(),
						event.getGuestsNumber(),
						false));

	}
	private void when(MemberCreatedEvent event) {

		memberMeetingsRepository.save(
			new MemberMeetingsView(
				event.getMemberId(),
				new ArrayList<>()));

	}
	private void when(MeetingMainAttributesChangedEvent event) {

		// TODO: use sql Query
		for (var memberMeeting : memberMeetingsRepository.findAll()) {
			var meeting = memberMeeting.getMeetings().stream()
					.filter(m -> m.getMeetingId().equals(event.getMeetingId()))
					.findAny().orElse(null);

			if (meeting != null) {
				meeting.setTermEndDate(event.getTermEndDate());
				meeting.setTitle(event.getTitle());
				meeting.setMeetingLocationAddress(event.getMeetingLocationAddress());
				meeting.setTermStartDate(event.getTermStartDate());
				meeting.setMeetingLocationPostalCode(event.getMeetingLocationPostalCode());
				meeting.setMeetingLocationCity(event.getMeetingLocationCity());
				meeting.setDescription(event.getDescription());
				meeting.setGuestsNumber(event.getGuestsLimit());
			}
		}

	}
	private void when(MeetingCanceledEvent event) {

		// TODO: use sql Query
		
		for (var memberMeeting : memberMeetingsRepository.findAll()) {
			var meeting = memberMeeting.getMeetings().stream()
					.filter(m -> m.getMeetingId().equals(event.getMeetingId()))
					.findAny().orElse(null);
			
			if (meeting != null) {
				memberMeeting.getMeetings().remove(meeting);
			}
		}

	}
	private void when(MeetingAttendeeRemovedEvent event) {

		var view = memberMeetingsRepository.findByMemberId(event.getAttendeeId());
		
		var meeting = view.getMeetings().stream()
				.filter(m -> m.getMeetingId().equals(event.getMeetingId()))
				.findAny().get();
		view.getMeetings().remove(meeting);

	}
	
	@Override
//	@Transactional//(propagation = Propagation.REQUIRES_NEW)
	public void project(IEvent event) {
		
		if (event instanceof MeetingAttendeeAddedEvent castedEvent) {
			when(castedEvent);
			return;
		}
		if (event instanceof MemberCreatedEvent castedEvent) {
			when(castedEvent);
			return;
		}
		if (event instanceof MeetingMainAttributesChangedEvent castedEvent) {
			when(castedEvent);
			return;
		}
		if (event instanceof MeetingCanceledEvent castedEvent) {
			when(castedEvent);
			return;
		}
		if (event instanceof MeetingAttendeeRemovedEvent castedEvent) {
			when(castedEvent);
			return;
		}
			
	}
	
}
