package com.example.meetup.meetings.domain.meetingGroupProposal;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.example.meetup.meetings.base.SystemClock;
import com.example.meetup.meetings.base.domain.Aggregate;
import com.example.meetup.meetings.base.domain.Entity;
import com.example.meetup.meetings.base.domain.IDomainEvent;
import com.example.meetup.meetings.domain.MeetingGroupLocation;
import com.example.meetup.meetings.domain.meetingGroup.MeetingGroup;
import com.example.meetup.meetings.domain.meetingGroupProposal.events.MeetingGroupProposalAcceptedEvent;
import com.example.meetup.meetings.domain.meetingGroupProposal.events.MeetingGroupProposalRejectedEvent;
import com.example.meetup.meetings.domain.meetingGroupProposal.events.MeetingGroupProposedEvent;
import com.example.meetup.meetings.domain.meetingGroupProposal.rules.MeetingGroupProposalCannotBeAcceptedMoreThanOnceRule;
import com.example.meetup.meetings.domain.member.MemberId;

import lombok.Getter;
import lombok.Setter;

@Getter
public class MeetingGroupProposal extends Aggregate {
	
	// business id
	@Setter // for testing
	protected MeetingGroupProposalId meetingGroupProposalId;
	
	public String getStreamId() {
		return "Meetings-MeetingGroupProposal-" + meetingGroupProposalId.getValue();
	}
		
	protected String name;
	protected String description;
	protected MeetingGroupLocation location;
	protected LocalDateTime proposalDate;
	protected MeetingGroupProposalStatus status;
	protected MemberId proposalUserId;
	protected String rejectionReason;
	
	public MeetingGroupProposal() {
	}
	
	public MeetingGroupProposal(String name, String description, MeetingGroupLocation location, MemberId proposalMemberId) {

		var meetingGroupProposed = new MeetingGroupProposedEvent(
			UUID.randomUUID(),
			description,
			location.getCity(),
			name,
			location.getCountryCode(),
			proposalMemberId.getValue(),
			SystemClock.now());
 
		apply(meetingGroupProposed);
		addDomainEvent(meetingGroupProposed);
	}
	
	public void accept() {
        checkRule(new MeetingGroupProposalCannotBeAcceptedMoreThanOnceRule(status));

		var meetingGroupProposalAccepted = new MeetingGroupProposalAcceptedEvent(
				meetingGroupProposalId.getValue());
 
		apply(meetingGroupProposalAccepted);
		addDomainEvent(meetingGroupProposalAccepted);
	}
	
	public void reject(String rejectionReason) {
        
		var meetingGroupProposalRejected = new MeetingGroupProposalRejectedEvent(
			meetingGroupProposalId.getValue(),
			rejectionReason);
 
		apply(meetingGroupProposalRejected);
		addDomainEvent(meetingGroupProposalRejected);
	}
	
	public MeetingGroup createMeetingGroup() {

		return new MeetingGroup(
				meetingGroupProposalId, 
				name, 
				description, 
				location != null ? location : new MeetingGroupLocation(null, null), 
				proposalUserId);
	}	
	
	
	private boolean when(MeetingGroupProposedEvent event) {
		meetingGroupProposalId = new MeetingGroupProposalId(event.getMeetingGroupProposalId());
		description = event.getDescription();
		name = event.getName();
		proposalUserId = new MemberId(event.getProposalUserId());
		proposalDate = event.getProposalDate();
		status = MeetingGroupProposalStatus.InVerification;
		location = new MeetingGroupLocation(event.getLocationCity(), event.getLocationCountryCode());
		return true;
	}
	
	private boolean when(MeetingGroupProposalAcceptedEvent event) {
		status = MeetingGroupProposalStatus.Accepted;
		return true;
	}
	
	private boolean when(MeetingGroupProposalRejectedEvent event) {
		rejectionReason = event.getRejectReason();
		status = MeetingGroupProposalStatus.Rejected;
		return true;
	}
	
	@Override
	protected boolean apply(IDomainEvent event) {
	
		var processed = false;

		if (event instanceof MeetingGroupProposedEvent castedEvent) {
			processed = when(castedEvent);
		}
		else if (event instanceof MeetingGroupProposalAcceptedEvent castedEvent) {
			processed = when(castedEvent);
		}	
		else if (event instanceof MeetingGroupProposalRejectedEvent castedEvent) {
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
