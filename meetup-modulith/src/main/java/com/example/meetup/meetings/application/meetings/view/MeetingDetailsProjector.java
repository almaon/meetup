package com.example.meetup.meetings.application.meetings.view;


import java.util.ArrayList;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.meetup.meetings.application.meetingGroups.view.GetMeetingGroupsByIdQuery;
import com.example.meetup.meetings.application.meetingGroups.view.MeetingGroupsView;
import com.example.meetup.meetings.base.application.IEvent;
import com.example.meetup.meetings.base.application.IProjector;
import com.example.meetup.meetings.base.infrastructure.IQueryDispatcher;
import com.example.meetup.meetings.domain.meeting.MeetingAttendeeRole;
import com.example.meetup.meetings.domain.meeting.events.MeetingAttendeeAddedEvent;
import com.example.meetup.meetings.domain.meeting.events.MeetingAttendeeRemovedEvent;
import com.example.meetup.meetings.domain.meeting.events.MeetingCanceledEvent;
import com.example.meetup.meetings.domain.meeting.events.MeetingCreatedEvent;
import com.example.meetup.meetings.domain.meeting.events.MeetingMainAttributesChangedEvent;
import com.example.meetup.meetings.domain.meeting.events.MemberSetAsAttendeeEvent;
import com.example.meetup.meetings.domain.meeting.events.NewMeetingHostSetEvent;

import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class MeetingDetailsProjector implements IProjector {

	private final MeetingDetailsRepository meetingDetailsRepository;
	

	private final IQueryDispatcher queryDispatcher;

	
	private void when(NewMeetingHostSetEvent event) {

		var view = meetingDetailsRepository.findByMeetingId(event.getMeetingId());
		
		view.getHosts().add(event.getHostId());

	}
	private void when(MeetingAttendeeAddedEvent event) {

		var view = meetingDetailsRepository.findByMeetingId(event.getMeetingId());
		
		view.setAttendeesCount(view.getAttendeesCount() + 1);
		view.setGuestsCount(view.getGuestsCount() + event.getGuestsNumber());
		
		if (MeetingAttendeeRole.valueOf(event.getRole()) == MeetingAttendeeRole.Host) {
			view.getHosts().add(event.getAttendeeId());
		}

	}
	private void when(MemberSetAsAttendeeEvent event) {

		var view = meetingDetailsRepository.findByMeetingId(event.getMeetingId());
		
		view.getHosts().remove(event.getMemberId());

	}
	private void when(MeetingMainAttributesChangedEvent event) {

		var view = meetingDetailsRepository.findByMeetingId(event.getMeetingId());
		
		view.setTitle(event.getTitle());
		view.setMeetingLocationPostalCode(event.getMeetingLocationPostalCode());
		view.setEventFeeValue(event.getEventFeeValue());
		view.setEventFeeCurrency(event.getEventFeeCurrency());
		view.setRsvpTermEndDate(event.getRsvpTermEndDate());
		view.setMeetingLocationName(event.getMeetingLocationName());
		view.setTermStartDate(event.getTermStartDate());
		view.setDescription(event.getDescription());
		view.setMeetingLocationAddress(event.getMeetingLocationAddress());
		view.setMeetingLocationCity(event.getMeetingLocationCity());
		view.setTermEndDate(event.getTermEndDate());
		view.setRsvpTermStartDate(event.getRsvpTermStartDate());
		view.setGuestsLimit(event.getGuestsLimit());
		view.setAttendeesLimit(event.getAttendeesLimit());

	}
	private void when(MeetingCanceledEvent event) {

		var view = meetingDetailsRepository.findByMeetingId(event.getMeetingId());
		
		view.setIsCanceled(true);

	}
	private void when(MeetingCreatedEvent event) {

		MeetingGroupsView meetingGroup = queryDispatcher.executeQuery(new GetMeetingGroupsByIdQuery(event.getMeetingGroupId()));

		meetingDetailsRepository.save(
			new MeetingDetailsView(
				event.getMeetingId(),
				event.getTitle(),
				event.getDescription(),
				event.getMeetingLocationCity(),
				event.getMeetingLocationPostalCode(),
				event.getMeetingLocationAddress(),
				event.getTermStartDate(),
				event.getTermEndDate(),
				0,
				0,
				event.getRsvpTermEndDate(),
				event.getRsvpTermStartDate(),
				event.getGuestsLimit(),
				event.getMeetingLocationName(),
				event.getEventFeeCurrency(),
				event.getEventFeeValue(),
				event.getMeetingGroupId(),
				meetingGroup.getName(),
				new ArrayList<>(),
				false,
				event.getAttendeesLimit()));

	}
	private void when(MeetingAttendeeRemovedEvent event) {

		var view = meetingDetailsRepository.findByMeetingId(event.getMeetingId());
		
		view.setAttendeesCount(view.getAttendeesCount() - 1);
		view.setGuestsCount(view.getGuestsCount() - event.getGuestsNumber());
		
		if (view.getHosts().contains(event.getAttendeeId())) {
			view.getHosts().remove(event.getAttendeeId());
		}

	}
	
	@Override
//	@Transactional//(propagation = Propagation.REQUIRES_NEW)
	public void project(IEvent event) {
		
		if (event instanceof NewMeetingHostSetEvent castedEvent) {
			when(castedEvent);
			return;
		}
		if (event instanceof MeetingAttendeeAddedEvent castedEvent) {
			when(castedEvent);
			return;
		}
		if (event instanceof MemberSetAsAttendeeEvent castedEvent) {
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
