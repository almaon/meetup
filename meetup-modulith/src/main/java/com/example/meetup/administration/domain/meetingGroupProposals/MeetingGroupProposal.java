package com.example.meetup.administration.domain.meetingGroupProposals;


import com.example.meetup.administration.base.SystemClock;
import com.example.meetup.administration.base.domain.Aggregate;
import com.example.meetup.administration.base.domain.IDomainEvent;
import com.example.meetup.administration.base.domain.Entity;

	
import com.example.meetup.administration.domain.meetingGroupProposals.events.MeetingGroupProposalAcceptedEvent;
	
import com.example.meetup.administration.domain.meetingGroupProposals.events.MeetingGroupProposalRejectedEvent;
	
import com.example.meetup.administration.domain.meetingGroupProposals.events.MeetingGroupProposalVerificationRequestedEvent;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.UUID;
import com.example.meetup.administration.domain.members.MemberId;
import com.example.meetup.administration.domain.administrator.AdministratorId;
import lombok.Getter;
import lombok.Setter;


@Getter
public class MeetingGroupProposal extends Aggregate {
	
	// business id
	@Setter // for testing
	protected MeetingGroupProposalId meetingGroupProposalId;
	
	public String getStreamId() {
		return "Administration-MeetingGroupProposal-" + meetingGroupProposalId.getValue();
	}
		
	protected String name;
	protected String description;
	protected MeetingGroupProposalStatus status;
	protected MeetingGroupLocation location;
	protected LocalDateTime proposalDate;
	protected MemberId proposalUserId;
	protected AdministratorId deciderId;
	protected String rejectReason;
	protected LocalDateTime decisionDate;
	
	public MeetingGroupProposal() {
	}
	
	public MeetingGroupProposal(MeetingGroupProposalId meetingGroupProposalId, String name, String description, MeetingGroupLocation location, MemberId proposalUserId, LocalDateTime proposalDate) {

		var meetingGroupProposalVerificationRequested = new MeetingGroupProposalVerificationRequestedEvent(
			meetingGroupProposalId.getValue(),
			description,
			name,
			proposalUserId.getValue(),
			location.getCity(),
			location.getCountryCode(),
			proposalDate);
 
		apply(meetingGroupProposalVerificationRequested);
		addDomainEvent(meetingGroupProposalVerificationRequested);

	}
	
	public void accept(AdministratorId administratorId) {

		var meetingGroupProposalAccepted = new MeetingGroupProposalAcceptedEvent(
			meetingGroupProposalId.getValue(),
			description,
			name,
			proposalUserId.getValue(),
			location.getCity(),
			location.getCountryCode(),
			proposalDate,
			SystemClock.now(),
			administratorId.getValue());
 
		apply(meetingGroupProposalAccepted);
		addDomainEvent(meetingGroupProposalAccepted);

	}	
	public void reject(AdministratorId administratorId, String rejectReason) {

		var meetingGroupProposalRejected = new MeetingGroupProposalRejectedEvent(
			meetingGroupProposalId.getValue(),
			rejectReason,
			SystemClock.now(),
			description,
			name,
			proposalUserId.getValue(),
			location.getCity(),
			location.getCountryCode(),
			proposalDate,
			administratorId.getValue());
 
		apply(meetingGroupProposalRejected);
		addDomainEvent(meetingGroupProposalRejected);

	}	
	
	
	
	private boolean when(MeetingGroupProposalRejectedEvent event) {

		status = MeetingGroupProposalStatus.Rejected;
		rejectReason = event.getDecisionRejectReason();
		deciderId = new AdministratorId(event.getAdminId());
		decisionDate = event.getDecisionDate();
		return true;

	}
	
	private boolean when(MeetingGroupProposalAcceptedEvent event) {

		status = MeetingGroupProposalStatus.Verified;
		deciderId = new AdministratorId(event.getAdminId());
		decisionDate = event.getDecisionDate();
		return true;

	}
	
	private boolean when(MeetingGroupProposalVerificationRequestedEvent event) {

		meetingGroupProposalId = new MeetingGroupProposalId(event.getMeetingGroupProposalId());
		description = event.getDescription();
		name = event.getName();
		proposalUserId = new MemberId(event.getProposalUserId());
		location = new MeetingGroupLocation(event.getName(), event.getDescription());
		proposalDate = event.getProposalDate();
		return true;

	}
	
	@Override
	protected boolean apply(IDomainEvent event) {
	
		var processed = false;

		if (event instanceof MeetingGroupProposalRejectedEvent castedEvent) {
			processed = when(castedEvent);
		}
		else if (event instanceof MeetingGroupProposalAcceptedEvent castedEvent) {
			processed = when(castedEvent);
		}
		else if (event instanceof MeetingGroupProposalVerificationRequestedEvent castedEvent) {
			processed = when(castedEvent);
		}
		
		if (processed)
			incrementVersion();
		
		return processed;
	}
	
	@Override
	public List<Entity> getDirectChildEntities() {
		var entities = new ArrayList<Entity>();
		return entities;
	}
	
	
}
