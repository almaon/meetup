package com.example.meetup.meetings.application.meetingGroupProposals.view;


import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import com.example.meetup.meetings.base.application.IProjector;
import com.example.meetup.meetings.base.application.IEvent;

import com.example.meetup.meetings.domain.meetingGroupProposal.events.MeetingGroupProposalRejectedEvent;
import com.example.meetup.meetings.domain.meetingGroupProposal.events.MeetingGroupProposedEvent;
import com.example.meetup.meetings.domain.meetingGroupProposal.MeetingGroupProposalStatus;
import com.example.meetup.meetings.domain.meetingGroupProposal.events.MeetingGroupProposalAcceptedEvent;

import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class MeetingGroupProposalsProjector implements IProjector {

	private final MeetingGroupProposalsRepository meetingGroupProposalsRepository;
	


	
	private void when(MeetingGroupProposedEvent event) {

		meetingGroupProposalsRepository.save(
			new MeetingGroupProposalsView(
				event.getMeetingGroupProposalId(),
				event.getName(),
				event.getDescription(),
				event.getLocationCountryCode(),
				event.getLocationCity(),
				event.getProposalUserId(),
				event.getProposalDate(),
				MeetingGroupProposalStatus.InVerification.name(),
				null));

	}
	private void when(MeetingGroupProposalRejectedEvent event) {

		var view = meetingGroupProposalsRepository.findByMeetingGroupProposalId(event.getMeetingGroupProposalId());
		
		view.setRejectReason(event.getRejectReason());
		view.setStatus(MeetingGroupProposalStatus.Rejected.name());

	}
	private void when(MeetingGroupProposalAcceptedEvent event) {

		var view = meetingGroupProposalsRepository.findByMeetingGroupProposalId(event.getMeetingGroupProposalId());
		
		view.setStatus(MeetingGroupProposalStatus.Accepted.name());

	}
	
	@Override
//	@Transactional//(propagation = Propagation.REQUIRES_NEW)
	public void project(IEvent event) {
		
		if (event instanceof MeetingGroupProposedEvent castedEvent) {
			when(castedEvent);
			return;
		}
		if (event instanceof MeetingGroupProposalRejectedEvent castedEvent) {
			when(castedEvent);
			return;
		}
		if (event instanceof MeetingGroupProposalAcceptedEvent castedEvent) {
			when(castedEvent);
			return;
		}
			
	}
	
}
