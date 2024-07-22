package com.example.meetup.meetings.application.meetings.view;


import java.util.ArrayList;


import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import com.example.meetup.meetings.base.application.IProjector;
import com.example.meetup.meetings.base.application.IEvent;

import com.example.meetup.meetings.domain.meeting.events.MeetingAttendeeRemovedEvent;
import com.example.meetup.meetings.domain.meeting.events.MeetingAttendeeFeePaidEvent;
import com.example.meetup.meetings.domain.meeting.events.MeetingAttendeeAddedEvent;
import com.example.meetup.meetings.domain.meeting.events.MeetingCreatedEvent;

import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class MeetingsMeetingFeesPaymentStatusProjector implements IProjector {

	private final MeetingsMeetingFeesPaymentStatusRepository meetingsMeetingFeesPaymentStatusRepository;
	


	
	private void when(MeetingAttendeeAddedEvent event) {

		var view = meetingsMeetingFeesPaymentStatusRepository.findByMeetingId(event.getMeetingId());
		
		view.getMeetingFeePaymentStatus().add(
				new MeetingFeePaymentStatus(
						event.getAttendeeId(), 
						false, 
						null));

	}
	private void when(MeetingAttendeeFeePaidEvent event) {

		var view = meetingsMeetingFeesPaymentStatusRepository.findByMeetingId(event.getMeetingId());
		
		var meetingFeePaymentStatus = view.getMeetingFeePaymentStatus().stream().filter(ps -> ps.getAttendeeId().equals(event.getAttendeeId())).findAny().get();
		
		meetingFeePaymentStatus.setIsPayed(true);
		meetingFeePaymentStatus.setPayedDate(event.getPayedDate());

	}
	private void when(MeetingCreatedEvent event) {

		meetingsMeetingFeesPaymentStatusRepository.save(
			new MeetingsMeetingFeesPaymentStatusView(
				event.getMeetingId(),
				new ArrayList<>()));

	}
	private void when(MeetingAttendeeRemovedEvent event) {

		var view = meetingsMeetingFeesPaymentStatusRepository.findByMeetingId(event.getMeetingId());
		
		var meetingFeePaymentStatus = view.getMeetingFeePaymentStatus().stream().filter(ps -> ps.getAttendeeId().equals(event.getAttendeeId())).findAny().get();

		view.getMeetingFeePaymentStatus().remove(meetingFeePaymentStatus);
		// TODO: what will happen if attendee already payed?

	}
	
	@Override
//	@Transactional//(propagation = Propagation.REQUIRES_NEW)
	public void project(IEvent event) {
		
		if (event instanceof MeetingAttendeeAddedEvent castedEvent) {
			when(castedEvent);
			return;
		}
		if (event instanceof MeetingAttendeeFeePaidEvent castedEvent) {
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
