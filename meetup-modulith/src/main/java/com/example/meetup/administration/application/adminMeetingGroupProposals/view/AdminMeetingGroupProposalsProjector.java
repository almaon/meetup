package com.example.meetup.administration.application.adminMeetingGroupProposals.view;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.meetup.administration.base.application.IEvent;
import com.example.meetup.administration.base.application.IProjector;
import com.example.meetup.administration.domain.meetingGroupProposals.MeetingGroupProposalStatus;
import com.example.meetup.administration.domain.meetingGroupProposals.events.MeetingGroupProposalAcceptedEvent;
import com.example.meetup.administration.domain.meetingGroupProposals.events.MeetingGroupProposalRejectedEvent;
import com.example.meetup.administration.domain.meetingGroupProposals.events.MeetingGroupProposalVerificationRequestedEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AdminMeetingGroupProposalsProjector implements IProjector {

	private final AdminMeetingGroupProposalsRepository adminMeetingGroupProposalsRepository;

	private void when(MeetingGroupProposalRejectedEvent event) {

		var view = adminMeetingGroupProposalsRepository.findByMeetingGroupProposalId(event.getMeetingGroupProposalId());

		view.setDecisionRejectReason(event.getDecisionRejectReason());
		view.setDecisionDate(event.getDecisionDate());
		view.setDeciderId(event.getAdminId());
		view.setStatus(MeetingGroupProposalStatus.Rejected);

	}

	private void when(MeetingGroupProposalAcceptedEvent event) {

		var view = adminMeetingGroupProposalsRepository.findByMeetingGroupProposalId(event.getMeetingGroupProposalId());

		view.setDecisionDate(event.getDecisionDate());
		view.setDeciderId(event.getAdminId());
		view.setStatus(MeetingGroupProposalStatus.Verified);

	}

	private void when(MeetingGroupProposalVerificationRequestedEvent event) {

		adminMeetingGroupProposalsRepository.save(new AdminMeetingGroupProposalsView(event.getMeetingGroupProposalId(),
				event.getDescription(), event.getName(), event.getProposalUserId(), event.getLocationCity(),
				event.getLocationCountryCode(), event.getProposalDate(), null, null,
				MeetingGroupProposalStatus.ToVerify, null));

	}

	@Override
//	@Transactional//(propagation = Propagation.REQUIRES_NEW)
	public void project(IEvent event) {

		if (event instanceof MeetingGroupProposalRejectedEvent castedEvent) {
			when(castedEvent);
			return;
		}
		if (event instanceof MeetingGroupProposalAcceptedEvent castedEvent) {
			when(castedEvent);
			return;
		}
		if (event instanceof MeetingGroupProposalVerificationRequestedEvent castedEvent) {
			when(castedEvent);
			return;
		}

	}

}
