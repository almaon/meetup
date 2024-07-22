package com.example.meetup.meetings.domain.meeting;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.UUID;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.example.meetup.meetings.base.SystemClock;
import com.example.meetup.meetings.domain.meeting.MeetingTerm;
import com.example.meetup.meetings.domain.meeting.Term;
import com.example.meetup.meetings.domain.meeting.events.MemberSetAsAttendeeEvent;
import com.example.meetup.meetings.domain.meeting.events.NewMeetingHostSetEvent;
import com.example.meetup.meetings.domain.meeting.rules.MeetingCannotBeChangedAfterStartRule;
import com.example.meetup.meetings.domain.meeting.rules.MeetingMustHaveAtLeastOneHostRule;
import com.example.meetup.meetings.domain.meeting.rules.MemberCannotHaveSetAttendeeRoleMoreThanOnceRule;
import com.example.meetup.meetings.domain.meeting.rules.OnlyMeetingOrGroupOrganizerCanSetMeetingMemberRolesRule;
import com.example.meetup.meetings.domain.member.MemberId;


public class MeetingRolesTests extends MeetingTestBase {

	@Test
	public void setHostRole_WhenMeetingHasStarted_IsNotPossible() {
		var creatorId = new MemberId(UUID.randomUUID());
		var meetingTestData = createMeetingTestData(new MeetingTestDataOptionsBuilder()
				.setCreatorId(creatorId)
				.setMeetingTerm(new MeetingTerm(SystemClock.now().plusHours(1), SystemClock.now().plusHours(3)))
				.createMeetingTestDataOptions());

        SystemClock.set(SystemClock.now().plusHours(2));

		assertBrokenRule(() -> {
			meetingTestData.getMeeting().setHostRole(meetingTestData.getMeetingGroup(), creatorId, creatorId);
		}, MeetingCannotBeChangedAfterStartRule.class);
	}

	@Test
	public void setHostRole_WhenSettingMemberIsNotAOrganizerOrHostMeeting_IsNotPossible() {
		var creatorId = new MemberId(UUID.randomUUID());
		var meetingTestData = createMeetingTestData(new MeetingTestDataOptionsBuilder()
				.setCreatorId(creatorId)
				.setRvspTerm(new Term(SystemClock.now().plusHours(1), SystemClock.now().plusHours(3)))
				.createMeetingTestDataOptions());
		var settingMemberId = new MemberId(UUID.randomUUID());
		var newOrganizerId = new MemberId(UUID.randomUUID());

        SystemClock.set(SystemClock.now().plusHours(2));

		meetingTestData.getMeetingGroup().joinToGroupMember(newOrganizerId);
		meetingTestData.getMeetingGroup().joinToGroupMember(settingMemberId);
		meetingTestData.getMeeting().addAttendee(meetingTestData.getMeetingGroup(), newOrganizerId, 0);

		assertBrokenRule(() -> {
			meetingTestData.getMeeting().setHostRole(meetingTestData.getMeetingGroup(), settingMemberId,
					newOrganizerId);
		}, OnlyMeetingOrGroupOrganizerCanSetMeetingMemberRolesRule.class);
	}

	@Test
	public void setHostRole_WhenSettingMemberIsGroupOrganizer_IsSuccessful() {
		var creatorId = new MemberId(UUID.randomUUID());
		var meetingTestData = createMeetingTestData(new MeetingTestDataOptionsBuilder()
				.setCreatorId(creatorId)
				.setRvspTerm(new Term(SystemClock.now().plusHours(1), SystemClock.now().plusHours(3)))
				.createMeetingTestDataOptions());
		var newOrganizerId = new MemberId(UUID.randomUUID());

        SystemClock.set(SystemClock.now().plusHours(2));

		meetingTestData.getMeetingGroup().joinToGroupMember(newOrganizerId);
		meetingTestData.getMeeting().addAttendee(meetingTestData.getMeetingGroup(), newOrganizerId, 0);

		meetingTestData.getMeeting().setHostRole(meetingTestData.getMeetingGroup(), creatorId, newOrganizerId);

        publishedDomainEvents.addAll(meetingTestData.getMeeting().getDomainEvents());
        meetingTestData.getMeeting().clearDomainEvents();
        
		var newMeetingHostSet = assertPublishedDomainEvent(NewMeetingHostSetEvent.class);
		assertThat(newMeetingHostSet.getHostId(), equalTo(newOrganizerId.getValue()));
	}

	@Test
	@Disabled
	public void setHostRole_WhenSettingMemberIsMeetingHost_IsSuccessful() {
		var creatorId = new MemberId(UUID.randomUUID());
		var meetingTestData = createMeetingTestData(new MeetingTestDataOptionsBuilder()
				.setCreatorId(creatorId)
				.setMeetingTerm(new MeetingTerm(SystemClock.now().plusHours(2), SystemClock.now().plusHours(3)))
				.setRvspTerm(new Term(SystemClock.now(), SystemClock.now().plusHours(2)))
				.createMeetingTestDataOptions());
		var newOrganizerId = new MemberId(UUID.randomUUID());
		var settingMemberId = new MemberId(UUID.randomUUID());

        SystemClock.set(SystemClock.now().plusHours(1));

		meetingTestData.getMeetingGroup().joinToGroupMember(newOrganizerId);
		meetingTestData.getMeetingGroup().joinToGroupMember(settingMemberId);
		meetingTestData.getMeeting().addAttendee(meetingTestData.getMeetingGroup(), newOrganizerId, 0);
		meetingTestData.getMeeting().addAttendee(meetingTestData.getMeetingGroup(), settingMemberId, 0);
		meetingTestData.getMeeting().setHostRole(meetingTestData.getMeetingGroup(), creatorId, newOrganizerId);

		meetingTestData.getMeeting().setHostRole(meetingTestData.getMeetingGroup(), settingMemberId, newOrganizerId);

        publishedDomainEvents.addAll(meetingTestData.getMeeting().getDomainEvents());
        meetingTestData.getMeeting().clearDomainEvents();
        
		var newMeetingHostSetEvent = assertPublishedDomainEvent(NewMeetingHostSetEvent.class);
		assertThat(newMeetingHostSetEvent.getHostId(), equalTo(newOrganizerId.getValue()));
	}

	@Test
	public void setAttendeeRole_WhenMeetingHasStarted_IsNotPossible() {
		var creatorId = new MemberId(UUID.randomUUID());
		var meetingTestData = createMeetingTestData(new MeetingTestDataOptionsBuilder()
				.setCreatorId(creatorId)
				.setMeetingTerm(new MeetingTerm(SystemClock.now().plusHours(1), SystemClock.now().plusHours(3)))
				.createMeetingTestDataOptions());

        SystemClock.set(SystemClock.now().plusHours(2));

		assertBrokenRule(() -> {
			meetingTestData.getMeeting().setAttendeeRole(meetingTestData.getMeetingGroup(), creatorId, creatorId);
		}, MeetingCannotBeChangedAfterStartRule.class);
	}

	@Test
	public void setAttendeeRole_WhenSettingMemberIsNotAOrganizerOrHostMeeting_IsNotPossible() {
		var creatorId = new MemberId(UUID.randomUUID());
		var meetingTestData = createMeetingTestData(new MeetingTestDataOptionsBuilder()
				.setCreatorId(creatorId)
				.setRvspTerm(new Term(SystemClock.now().plusHours(1), SystemClock.now().plusHours(3)))
				.createMeetingTestDataOptions());
		var settingMemberId = new MemberId(UUID.randomUUID());
		var newOrganizerId = new MemberId(UUID.randomUUID());

        SystemClock.set(SystemClock.now().plusHours(2));

		meetingTestData.getMeetingGroup().joinToGroupMember(newOrganizerId);
		meetingTestData.getMeetingGroup().joinToGroupMember(settingMemberId);
		meetingTestData.getMeeting().addAttendee(meetingTestData.getMeetingGroup(), newOrganizerId, 0);

		assertBrokenRule(() -> {
			meetingTestData.getMeeting().setAttendeeRole(meetingTestData.getMeetingGroup(), settingMemberId,
					newOrganizerId);
		}, OnlyMeetingOrGroupOrganizerCanSetMeetingMemberRolesRule.class);
	}

	@Test
	public void setAttendeeRole_WhenMemberIsOrganizer_IsSuccessful() {
		var creatorId = new MemberId(UUID.randomUUID());
		var meetingTestData = createMeetingTestData(new MeetingTestDataOptionsBuilder()
				.setCreatorId(creatorId)
				.setRvspTerm(new Term(SystemClock.now(), SystemClock.now().plusHours(3)))
				.createMeetingTestDataOptions()
		);
		
        SystemClock.set(SystemClock.now().plusHours(2));

		var newOrganizerId = new MemberId(UUID.randomUUID());
		meetingTestData.getMeetingGroup().joinToGroupMember(newOrganizerId);
		meetingTestData.getMeeting().addAttendee(meetingTestData.getMeetingGroup(), newOrganizerId, 0);
		meetingTestData.getMeeting().setHostRole(meetingTestData.getMeetingGroup(), creatorId, newOrganizerId);

		meetingTestData.getMeeting().setAttendeeRole(meetingTestData.getMeetingGroup(), creatorId, newOrganizerId);

		publishedDomainEvents.addAll(meetingTestData.getMeeting().getDomainEvents());
        meetingTestData.getMeeting().clearDomainEvents();
        
		var newMeetingHostSet = assertPublishedDomainEvent(MemberSetAsAttendeeEvent.class);
		assertThat(newMeetingHostSet.getMemberId(), equalTo(newOrganizerId.getValue()));
	}

	@Test
	public void setAttendeeRole_WhenMemberIsAlreadyAttendee_IsNotPossible() {
		var creatorId = new MemberId(UUID.randomUUID());
		var meetingTestData = createMeetingTestData(new MeetingTestDataOptionsBuilder()
				.setCreatorId(creatorId)
				.setRvspTerm(new Term(SystemClock.now(), SystemClock.now().plusHours(3)))
				.createMeetingTestDataOptions());
		
        SystemClock.set(SystemClock.now().plusHours(2));

		var attendeeId = new MemberId(UUID.randomUUID());
		meetingTestData.getMeetingGroup().joinToGroupMember(attendeeId);
		meetingTestData.getMeeting().addAttendee(meetingTestData.getMeetingGroup(), attendeeId, 0);

		assertBrokenRule(() -> {
			meetingTestData.getMeeting().setAttendeeRole(meetingTestData.getMeetingGroup(), creatorId, attendeeId);
		}, MemberCannotHaveSetAttendeeRoleMoreThanOnceRule.class);
	}

	@Test
	public void setAttendeeRole_ForLastOrganizer_BreaksMeetingMustHaveAtLeastOneHostRule() {
		var creatorId = new MemberId(UUID.randomUUID());
		var meetingTestData = createMeetingTestData(new MeetingTestDataOptionsBuilder().
				setCreatorId(creatorId)
				.createMeetingTestDataOptions());

		assertBrokenRule(() -> {
			meetingTestData.getMeeting().setAttendeeRole(meetingTestData.getMeetingGroup(), creatorId, creatorId);
		}, MeetingMustHaveAtLeastOneHostRule.class);
	}
}