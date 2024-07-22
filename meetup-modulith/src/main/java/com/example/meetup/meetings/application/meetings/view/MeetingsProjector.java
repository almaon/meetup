package com.example.meetup.meetings.application.meetings.view;


import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.meetup.meetings.application.meetingGroups.view.GetMeetingGroupsByIdQuery;
import com.example.meetup.meetings.application.meetingGroups.view.MeetingGroupsView;
import com.example.meetup.meetings.base.application.IEvent;
import com.example.meetup.meetings.base.application.IProjector;
import com.example.meetup.meetings.base.infrastructure.IQueryDispatcher;
import com.example.meetup.meetings.domain.meeting.events.MeetingCanceledEvent;
import com.example.meetup.meetings.domain.meeting.events.MeetingCreatedEvent;
import com.example.meetup.meetings.domain.meeting.events.MeetingMainAttributesChangedEvent;

import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class MeetingsProjector implements IProjector {

	private final MeetingsRepository meetingsRepository;
	

	private final IQueryDispatcher queryDispatcher;

	
	private void when(MeetingMainAttributesChangedEvent event) {

		var view = meetingsRepository.findByMeetingId(event.getMeetingId());
		
		view.setTitle(event.getTitle());
		view.setMeetingLocationPostalCode(event.getMeetingLocationPostalCode());
		view.setTermStartDate(event.getTermStartDate());
		view.setDescription(event.getDescription());
		view.setMeetingLocationAddress(event.getMeetingLocationAddress());
		view.setMeetingLocationCity(event.getMeetingLocationCity());
		view.setTermEndDate(event.getTermEndDate());

	}
	private void when(MeetingCanceledEvent event) {

		var view = meetingsRepository.findByMeetingId(event.getMeetingId());
		
		view.setIsCanceled(true);

	}
	private void when(MeetingCreatedEvent event) {

		MeetingGroupsView meetingGroup = queryDispatcher.executeQuery(new GetMeetingGroupsByIdQuery(event.getMeetingGroupId()));
		
		meetingsRepository.save(
			new MeetingsView(
				event.getMeetingId(),
				event.getTitle(),
				event.getDescription(),
				event.getMeetingLocationPostalCode(),
				event.getMeetingLocationAddress(),
				event.getMeetingLocationCity(),
				event.getTermStartDate(),
				event.getTermEndDate(),
				event.getMeetingGroupId(),
				meetingGroup.getName(),
				false));

	}
	
	@Override
//	@Transactional//(propagation = Propagation.REQUIRES_NEW)
	public void project(IEvent event) {
		
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
			
	}
	
}
