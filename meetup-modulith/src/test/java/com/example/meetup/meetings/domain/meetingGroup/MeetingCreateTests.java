package com.example.meetup.meetings.domain.meetingGroup;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.example.meetup.meetings.base.SystemClock;
import com.example.meetup.meetings.domain.MeetingGroupLocation;
import com.example.meetup.meetings.domain.MoneyValue;
import com.example.meetup.meetings.domain.base.TestBase;
import com.example.meetup.meetings.domain.meeting.MeetingLocation;
import com.example.meetup.meetings.domain.meeting.MeetingTerm;
import com.example.meetup.meetings.domain.meeting.Term;
import com.example.meetup.meetings.domain.meeting.events.MeetingCreatedEvent;
import com.example.meetup.meetings.domain.meetingGroup.events.MeetingGroupGeneralAttributesEditedEvent;
import com.example.meetup.meetings.domain.meetingGroup.events.MeetingGroupMemberLeftGroupEvent;
import com.example.meetup.meetings.domain.meetingGroup.events.MeetingGroupPaymentInfoUpdatedEvent;
import com.example.meetup.meetings.domain.meetingGroup.events.NewMeetingGroupMemberJoinedEvent;
import com.example.meetup.meetings.domain.meetingGroup.rules.MeetingCanBeOrganizedOnlyByPayedGroupRule;
import com.example.meetup.meetings.domain.meetingGroup.rules.MeetingGroupMemberCannotBeAddedTwiceRule;
import com.example.meetup.meetings.domain.meetingGroup.rules.MeetingHostMustBeAMeetingGroupMemberRule;
import com.example.meetup.meetings.domain.meetingGroup.rules.NotActualGroupMemberCannotLeaveGroupRule;
import com.example.meetup.meetings.domain.meetingGroupProposal.MeetingGroupProposal;
import com.example.meetup.meetings.domain.member.MemberId;

public class MeetingCreateTests extends TestBase {

	@Test
	public void editGeneralAttributes_IsSuccessful() {
		var meetingGroup = createMeetingGroup();

		var meetingGroupLocation = new MeetingGroupLocation("London", "GB");
		meetingGroup.editGeneralAttributes("newName", "newDescription", meetingGroupLocation);

        publishedDomainEvents.addAll(meetingGroup.getDomainEvents());

		var meetingGroupGeneralAttributesEdited = assertPublishedDomainEvent(
				MeetingGroupGeneralAttributesEditedEvent.class);

		assertThat(meetingGroupGeneralAttributesEdited.getNewName(), equalTo("newName"));
		assertThat(meetingGroupGeneralAttributesEdited.getNewDescription(), equalTo("newDescription"));
		assertThat(new MeetingGroupLocation(
				meetingGroupGeneralAttributesEdited.getNewLocationCity(),
				meetingGroupGeneralAttributesEdited.getNewLocationCountryCode()), equalTo(meetingGroupLocation));
	}

	@Test
	public void joinToGroup_WhenMemberHasNotJoinedYet_IsSuccessful() {
		var meetingGroup = createMeetingGroup();

		MemberId newMemberId = new MemberId(UUID.randomUUID());
		meetingGroup.clearDomainEvents();
		meetingGroup.joinToGroupMember(newMemberId);

        publishedDomainEvents.addAll(meetingGroup.getDomainEvents());

		var newMeetingGroupMemberJoined = assertPublishedDomainEvent(
				NewMeetingGroupMemberJoinedEvent.class);

		assertThat(newMeetingGroupMemberJoined.getMeetingGroupId(), equalTo(meetingGroup.getMeetingGroupId().getValue()));
		assertThat(newMeetingGroupMemberJoined.getMemberId(), equalTo(newMemberId.getValue()));
		assertThat(newMeetingGroupMemberJoined.getRole(), equalTo(MeetingGroupMemberRole.Member.name()));
	}

	@Test
	public void joinToGroup_WhenMemberHasAlreadyJoined_BreaksMeetingGroupMemberCannotBeAddedTwiceRule() {
		var meetingGroup = createMeetingGroup();

		MemberId newMemberId = new MemberId(UUID.randomUUID());
		meetingGroup.joinToGroupMember(newMemberId);

		assertBrokenRule(() -> meetingGroup.joinToGroupMember(newMemberId),
				MeetingGroupMemberCannotBeAddedTwiceRule.class);
	}

	@Test
	public void leaveGroup_WhenMemberIsActiveMemberOfGroup_IsSuccessful() {
		var organizer = new MemberId(UUID.randomUUID());
		var meetingGroup = createMeetingGroup(organizer);
		var newMemberId = new MemberId(UUID.randomUUID());
		meetingGroup.joinToGroupMember(newMemberId);
		
		meetingGroup.leaveGroup(newMemberId);
		
        publishedDomainEvents.addAll(meetingGroup.getDomainEvents());

		var meetingGroupMemberLeft = assertPublishedDomainEvent(
				MeetingGroupMemberLeftGroupEvent.class);

		assertThat(meetingGroupMemberLeft.getMemberId(), equalTo(newMemberId.getValue()));
	}

	@Test
	public void leaveGroup_WhenMemberIsNotActiveMemberOfGroup_BreaksNotActualGroupMemberCannotLeaveGroupRule() {
		var meetingGroup = createMeetingGroup();
		var newMemberId = new MemberId(UUID.randomUUID());

		assertBrokenRule(() -> meetingGroup.leaveGroup(newMemberId), NotActualGroupMemberCannotLeaveGroupRule.class);
	}

	@Test
	public void updatePaymentDateTo_IsSuccessful() {
		var meetingGroup = createMeetingGroup();

		meetingGroup.setExpirationDate(SystemClock.now());

        publishedDomainEvents.addAll(meetingGroup.getDomainEvents());

		var meetingGroupPaymentInfoUpdated = assertPublishedDomainEvent(
				MeetingGroupPaymentInfoUpdatedEvent.class);

		assertThat(meetingGroupPaymentInfoUpdated.getMeetingGroupId(), equalTo(meetingGroup.getMeetingGroupId().getValue()));
		assertThat(meetingGroupPaymentInfoUpdated.getPaymentDateTo(), equalTo(SystemClock.now()));
	}

	@Test
	public void createMeeting_WhenGroupIsNotPayed_IsNotPossible() {
		var meetingGroup = createMeetingGroup();

		MemberId creatorId = new MemberId(UUID.randomUUID());

		assertBrokenRule(() -> {
			meetingGroup.createMeeting(
					"title", 
					new MeetingTerm(null, null),
					"description", 
					new MeetingLocation("Name", "Address", "PostalCode", "City"), 
					10, 
					0,
					new Term(null, null), 
					MoneyValue.undefined(), 
					new ArrayList<MemberId>(),
					creatorId);
		}, MeetingCanBeOrganizedOnlyByPayedGroupRule.class);
	}

	@Test
	public void createMeeting_WhenCreatorIsMemberOfGroupAndHostsAreNotDefined_IsSuccessful() {
		var creatorId = new MemberId(UUID.randomUUID());
		var meetingGroup = createMeetingGroup(creatorId);
		meetingGroup.setExpirationDate(SystemClock.now().plusHours(1));

		var meeting = meetingGroup.createMeeting("title", 
				new MeetingTerm(null, null),
				"description", 
				new MeetingLocation("Name", "Address", "PostalCode", "City"), 
				10, 
				0,
				new Term(null, null), 
				MoneyValue.undefined(), 
				new ArrayList<MemberId>(),
				creatorId);

        publishedDomainEvents.addAll(meeting.getDomainEvents());

		assertPublishedDomainEvent(MeetingCreatedEvent.class);
	}

	@Test
	public void createMeeting_WhenHostsAreDefinedAndTheyAreNotGroupMembers_BreaksMeetingHostMustBeAMeetingGroupMemberRule() {
		var definedProposalMemberId = new MemberId(UUID.randomUUID());
		var meetingGroup = createMeetingGroup(definedProposalMemberId);
		meetingGroup.setExpirationDate(SystemClock.now().plusHours(1));
		var hostOne = new MemberId(UUID.randomUUID());
		var hostTwo = new MemberId(UUID.randomUUID());
		List<MemberId> hosts = new ArrayList<MemberId>();
		hosts.add(hostOne);
		hosts.add(hostTwo);


		assertBrokenRule(() -> {
			meetingGroup.createMeeting(
					"title", 
					new MeetingTerm(null, null),
					"description", 
					new MeetingLocation("Name", "Address", "PostalCode", "City"), 
					10, 
					0,
					new Term(null, null), 
					MoneyValue.undefined(),
					hosts, 
					definedProposalMemberId);
		}, MeetingHostMustBeAMeetingGroupMemberRule.class);
	}

	@Test
	public void createMeeting_WhenCreatorIsNotMemberOfGroup_BreaksMeetingHostMustBeAMeetingGroupMemberRule() {
		var definedProposalMemberId = new MemberId(UUID.randomUUID());
		var creatorId = new MemberId(UUID.randomUUID());
		var meetingGroup = createMeetingGroup(definedProposalMemberId);
		meetingGroup.setExpirationDate(SystemClock.now().plusHours(1));

		assertBrokenRule(() -> {
			meetingGroup.createMeeting(
					"title", 
					new MeetingTerm(null, null),
					"description", 
					new MeetingLocation("Name", "Address", "PostalCode", "City"), 
					10, 
					0,
					new Term(null, null), 
					MoneyValue.undefined(), 
					new ArrayList<MemberId>(),
					creatorId);
		}, MeetingHostMustBeAMeetingGroupMemberRule.class);
	}

	private MeetingGroup createMeetingGroup() {
		return createMeetingGroup(null);
	}

	private MeetingGroup createMeetingGroup(MemberId definedProposalMemberId) {
		var proposalMemberId = definedProposalMemberId != null ? definedProposalMemberId
				: new MemberId(UUID.randomUUID());
		var meetingProposal = new MeetingGroupProposal("name", "description",
				new MeetingGroupLocation("Warsaw", "PL"), proposalMemberId);

		meetingProposal.accept();

		var meetingGroup = meetingProposal.createMeetingGroup();

		clearPublishedDomainEventsList();

		return meetingGroup;
	}

}
