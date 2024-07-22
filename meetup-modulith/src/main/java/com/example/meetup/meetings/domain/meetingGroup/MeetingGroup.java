package com.example.meetup.meetings.domain.meetingGroup;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.meetup.meetings.base.SystemClock;
import com.example.meetup.meetings.base.domain.Aggregate;
import com.example.meetup.meetings.base.domain.Entity;
import com.example.meetup.meetings.base.domain.IDomainEvent;
import com.example.meetup.meetings.domain.MeetingGroupLocation;
import com.example.meetup.meetings.domain.MoneyValue;
import com.example.meetup.meetings.domain.meeting.Meeting;
import com.example.meetup.meetings.domain.meeting.MeetingLimits;
import com.example.meetup.meetings.domain.meeting.MeetingLocation;
import com.example.meetup.meetings.domain.meeting.MeetingTerm;
import com.example.meetup.meetings.domain.meeting.Term;
import com.example.meetup.meetings.domain.meetingGroup.events.MeetingGroupCreatedEvent;
import com.example.meetup.meetings.domain.meetingGroup.events.MeetingGroupGeneralAttributesEditedEvent;
import com.example.meetup.meetings.domain.meetingGroup.events.MeetingGroupPaymentInfoUpdatedEvent;
import com.example.meetup.meetings.domain.meetingGroup.events.NewMeetingGroupMemberJoinedEvent;
import com.example.meetup.meetings.domain.meetingGroup.rules.MeetingCanBeOrganizedOnlyByPayedGroupRule;
import com.example.meetup.meetings.domain.meetingGroup.rules.MeetingGroupMemberCannotBeAddedTwiceRule;
import com.example.meetup.meetings.domain.meetingGroup.rules.MeetingGroupMustHaveAtLeastOneOrganizerRule;
import com.example.meetup.meetings.domain.meetingGroup.rules.MeetingHostMustBeAMeetingGroupMemberRule;
import com.example.meetup.meetings.domain.meetingGroup.rules.NotActualGroupMemberCannotLeaveGroupRule;
import com.example.meetup.meetings.domain.meetingGroupProposal.MeetingGroupProposalId;
import com.example.meetup.meetings.domain.member.MemberId;

import lombok.Getter;
import lombok.Setter;

@Getter
public class MeetingGroup extends Aggregate {

	// business id
	@Setter // for testing
	protected MeetingGroupId meetingGroupId;

	public String getStreamId() {
		return "Meetings-MeetingGroup-" + meetingGroupId.getValue();
	}

	protected String name;
	protected String description;
	protected MemberId creatorId;
	protected List<MeetingGroupMember> members = new ArrayList<>();
	protected MeetingGroupLocation location;
	protected LocalDateTime paymentDateTo;

	public MeetingGroup() {
	}

	public MeetingGroup(MeetingGroupProposalId meetingGroupProposalId, String name, String description, MeetingGroupLocation location, MemberId creatorId) {
        
		var meetingGroupCreated = new MeetingGroupCreatedEvent(
				meetingGroupProposalId.getValue(),
				creatorId.getValue(),
				description,
				location.getCity(),
				name,
				location.getCountryCode());

		apply(meetingGroupCreated);
		addDomainEvent(meetingGroupCreated);

		var organizerJoinedNewMeetingGroup = new NewMeetingGroupMemberJoinedEvent(
				meetingGroupProposalId.getValue(),
				creatorId.getValue(),
				MeetingGroupMemberRole.Organizer.name(),
				SystemClock.now(),
				false);

		apply(organizerJoinedNewMeetingGroup);
		addDomainEvent(organizerJoinedNewMeetingGroup);
	}

	public void editGeneralAttributes(String name, String description, MeetingGroupLocation location) {
		
		var meetingGroupGeneralAttributesEdited = new MeetingGroupGeneralAttributesEditedEvent(
				name,
				description,
				meetingGroupId.getValue(),
				location.getCity(),
				location.getCountryCode());

		apply(meetingGroupGeneralAttributesEdited);
		addDomainEvent(meetingGroupGeneralAttributesEdited);
	}

	public void joinToGroupMember(MemberId memberId) {
        checkRule(new MeetingGroupMemberCannotBeAddedTwiceRule(members, memberId));

        var isInactiveMember = members.stream().anyMatch(m -> m.getMemberId().equals(memberId));
        
		var newMeetingGroupMemberJoined = new NewMeetingGroupMemberJoinedEvent(
				meetingGroupId.getValue(),
				memberId.getValue(),
				MeetingGroupMemberRole.Member.name(),
				SystemClock.now(),
				isInactiveMember);

		apply(newMeetingGroupMemberJoined);
		addDomainEvent(newMeetingGroupMemberJoined);
	}

	public void setExpirationDate(LocalDateTime dateTo) {
		
		var meetingGroupPaymentInfoUpdated = new MeetingGroupPaymentInfoUpdatedEvent(
				meetingGroupId.getValue(),
				dateTo);

		apply(meetingGroupPaymentInfoUpdated);
		addDomainEvent(meetingGroupPaymentInfoUpdated);
	}

	public void leaveGroup(MemberId memberId) {
        checkRule(new NotActualGroupMemberCannotLeaveGroupRule(members, memberId));
        if (isOrganizer(memberId)) {
        	var organizersCount = members.stream().filter(m -> m.isOrganizer(m.getMemberId())).count();
        	checkRule(new MeetingGroupMustHaveAtLeastOneOrganizerRule((int) organizersCount - 1));
        }
        
		members.stream().filter(m -> m.getMemberId().equals(memberId)).findAny().get().leave();
	}

	public boolean isMemberOfGroup(MemberId attendeeId) {
		return members.stream().anyMatch(m -> m.isMember(attendeeId));
	}

	public boolean isOrganizer(MemberId memberId) {
		return members.stream().anyMatch(m -> m.isOrganizer(memberId));
	}

	public Meeting createMeeting(String title, MeetingTerm term, String description, MeetingLocation location, int attendeesLimit, int guestsLimit, Term rsvpTerm, MoneyValue eventFee, List<MemberId> hostsMembersIds, MemberId creatorId) {	
        checkRule(new MeetingCanBeOrganizedOnlyByPayedGroupRule(paymentDateTo));
        checkRule(new MeetingHostMustBeAMeetingGroupMemberRule(creatorId, hostsMembersIds, members));
        
		return new Meeting(
				meetingGroupId, 
				title, 
				term, 
				description, 
				location, 
				new MeetingLimits(attendeesLimit, guestsLimit), 
				rsvpTerm, 
				eventFee, 
				hostsMembersIds,
				creatorId);
	}	


	private boolean when(MeetingGroupCreatedEvent event) {

		meetingGroupId = new MeetingGroupId(event.getMeetingGroupId());
		creatorId = new MemberId(event.getCreatorId());
		description = event.getDescription();
		name = event.getName();
		location = new MeetingGroupLocation(event.getLocationCity(), event.getLocationCountryCode());
		return true;
	}

	private boolean when(NewMeetingGroupMemberJoinedEvent event) {

		var memberId = new MemberId(event.getMemberId());
		
		if( event.isWasInactiveMember()) {
			members.stream().filter(m -> m.getMemberId().equals(memberId))
				.findAny().get().rejoin();
		} else {			
			members.add(new MeetingGroupMember(
					this, 
					meetingGroupId, 
					memberId, 
					MeetingGroupMemberRole.valueOf(event.getRole()),
					event.getJoinedDate()));
		}

		return true;
	}

	private boolean when(MeetingGroupPaymentInfoUpdatedEvent event) {

		paymentDateTo = event.getPaymentDateTo();
		return true;
	}

	private boolean when(MeetingGroupGeneralAttributesEditedEvent event) {

		name = event.getNewName();
		description = event.getNewDescription();
		location = new MeetingGroupLocation(
				event.getNewLocationCity(), 
				event.getNewLocationCountryCode());

		return true;
	}

	@Override
	protected boolean apply(IDomainEvent event) {

		var processed = false;

		if (event instanceof MeetingGroupCreatedEvent castedEvent) {
			processed = when(castedEvent);
		}
		else if (event instanceof NewMeetingGroupMemberJoinedEvent castedEvent) {
			processed = when(castedEvent);
		}
		else if (event instanceof MeetingGroupPaymentInfoUpdatedEvent castedEvent) {
			processed = when(castedEvent);
		}
		else if (event instanceof MeetingGroupGeneralAttributesEditedEvent castedEvent) {
			processed = when(castedEvent);
		}
		
		if (processed)
			incrementVersion();
			
		return processed;	
	}

	@Override
	public List<Entity> getDirectChildEntities() {
		var entities = new ArrayList<Entity>();
		entities.addAll(members);
		return entities;
	}


}
