package com.example.meetup.payments.application.meetingFees.view;


import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import com.example.meetup.payments.base.application.IProjector;
import com.example.meetup.payments.base.application.IEvent;

import com.example.meetup.payments.domain.meetingFees.events.MeetingFeeCreatedEvent;
import com.example.meetup.payments.domain.meetingFees.events.MeetingFeeCanceledEvent;
import com.example.meetup.payments.domain.meetingFees.events.MeetingFeeExpiredEvent;
import com.example.meetup.payments.domain.meetingFees.events.MeetingFeePaidEvent;

import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class MeetingFeesProjector implements IProjector {

	private final MeetingFeesRepository meetingFeesRepository;
	


	
	private void when(MeetingFeeCanceledEvent event) {

		var view = meetingFeesRepository.findByMeetingFeeId(event.getMeetingFeeId());
		
		view.setStatus(event.getStatus());

	}
	private void when(MeetingFeeCreatedEvent event) {

		meetingFeesRepository.save(
			new MeetingFeesView(
				event.getMeetingFeeId(),
				event.getPayerId(),
				event.getMeetingId(),
				event.getFeeValue(),
				event.getFeeCurrency(),
				event.getStatus()));

	}
	private void when(MeetingFeeExpiredEvent event) {

		var view = meetingFeesRepository.findByMeetingFeeId(event.getMeetingFeeId());
		
		view.setStatus(event.getStatus());

	}
	private void when(MeetingFeePaidEvent event) {

		var view = meetingFeesRepository.findByMeetingFeeId(event.getMeetingFeeId());
		
		view.setStatus(event.getStatus());

	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void project(IEvent event) {
		
		if (event instanceof MeetingFeeCanceledEvent castedEvent) {
			when(castedEvent);
			return;
		}
		if (event instanceof MeetingFeeCreatedEvent castedEvent) {
			when(castedEvent);
			return;
		}
		if (event instanceof MeetingFeeExpiredEvent castedEvent) {
			when(castedEvent);
			return;
		}
		if (event instanceof MeetingFeePaidEvent castedEvent) {
			when(castedEvent);
			return;
		}
			
	}
	
}
