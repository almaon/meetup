package com.example.meetup.meetings.domain.meeting;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.example.meetup.meetings.base.SystemClock;
import com.example.meetup.meetings.domain.meeting.MeetingTerm;
import com.example.meetup.meetings.domain.meeting.Term;
import com.example.meetup.meetings.domain.meeting.rules.AttendeeCanBeAddedOnlyInRsvpTermRule;
import com.example.meetup.meetings.domain.meeting.rules.MeetingAttendeeMustBeAMemberOfGroupRule;
import com.example.meetup.meetings.domain.meeting.rules.MeetingAttendeesNumberIsAboveLimitRule;
import com.example.meetup.meetings.domain.meeting.rules.MeetingCannotBeChangedAfterStartRule;
import com.example.meetup.meetings.domain.meeting.rules.MeetingGuestsNumberIsAboveLimitRule;
import com.example.meetup.meetings.domain.meeting.rules.MemberCannotBeAnAttendeeOfMeetingMoreThanOnceRule;
import com.example.meetup.meetings.domain.member.MemberId;

class MeetingAddAttendeeTests extends MeetingTestBase {

	@Test
	public void addAttendee_WhenMeetingHasStared_IsNotPossible() {
		var creatorId = new MemberId(UUID.randomUUID());
		var meetingTestData = createMeetingTestData(new MeetingTestDataOptionsBuilder()
				.setCreatorId(creatorId)
				.setMeetingTerm(new MeetingTerm(SystemClock.now().minusHours(1), SystemClock.now().plusHours(3)))

				.createMeetingTestDataOptions());

		assertBrokenRule(
				() -> meetingTestData.getMeeting().addAttendee(meetingTestData.getMeetingGroup(), creatorId, 0),
				MeetingCannotBeChangedAfterStartRule.class);
	}

	@Test
	public void addAttendee_WhenRsvpTermEnded_IsNotPossible() {
		var creatorId = new MemberId(UUID.randomUUID());
		var meetingTestData = createMeetingTestData(new MeetingTestDataOptionsBuilder()
				.setCreatorId(creatorId)
				.setRvspTerm(new Term(SystemClock.now().plusHours(1), SystemClock.now().plusHours(3)))
				.createMeetingTestDataOptions());
        
		var attendee = new MemberId(UUID.randomUUID());
		meetingTestData.getMeetingGroup().joinToGroupMember(attendee);
		
		assertBrokenRule(
				() -> meetingTestData.getMeeting().addAttendee(meetingTestData.getMeetingGroup(), attendee, 0),
				AttendeeCanBeAddedOnlyInRsvpTermRule.class);
	}

	@Test
	public void addAttendee_WhenAttendeeIsNotAMemberOfMeetingGroup_IsNotPossible() {
		var creatorId = new MemberId(UUID.randomUUID());
		var meetingTestData = createMeetingTestData(new MeetingTestDataOptionsBuilder()
				.setRvspTerm(new Term(SystemClock.now().minusHours(1), SystemClock.now().plusHours(3)))
				.setCreatorId(creatorId)
				.createMeetingTestDataOptions());

		assertBrokenRule(() -> meetingTestData.getMeeting().addAttendee(meetingTestData.getMeetingGroup(),
				new MemberId(UUID.randomUUID()), 0), MeetingAttendeeMustBeAMemberOfGroupRule.class);
	}

	@Test
	public void addAttendee_WhenMemberIsAlreadyAttendeeOfMeeting_IsNotPossible() {

		var creatorId = new MemberId(UUID.randomUUID());
		var meetingTestData = createMeetingTestData(new MeetingTestDataOptionsBuilder()
				.setRvspTerm(new Term(SystemClock.now().minusHours(1), SystemClock.now().plusHours(3)))
				.setCreatorId(creatorId)
				.createMeetingTestDataOptions());
		var newMemberId = new MemberId(UUID.randomUUID());

		meetingTestData.getMeetingGroup().joinToGroupMember(newMemberId);
		meetingTestData.getMeeting().addAttendee(meetingTestData.getMeetingGroup(), newMemberId, 0);

		assertBrokenRule(
				() -> meetingTestData.getMeeting().addAttendee(meetingTestData.getMeetingGroup(), newMemberId, 0),
				MemberCannotBeAnAttendeeOfMeetingMoreThanOnceRule.class);

	}

	@Test
	public void addAttendee_WhenGuestsNumberIsAboveTheLimit_IsNotPossible() {
		var creatorId = new MemberId(UUID.randomUUID());
		var meetingTestData = createMeetingTestData(new MeetingTestDataOptionsBuilder()
				.setCreatorId(creatorId)
				.setAttendeesLimit(10)
				.setGuestsLimit(5)
				.setRvspTerm(new Term(SystemClock.now().minusHours(1), SystemClock.now().plusHours(3)))
				.createMeetingTestDataOptions());
		var newMemberId = new MemberId(UUID.randomUUID());

		meetingTestData.getMeetingGroup().joinToGroupMember(newMemberId);

		assertBrokenRule(
				() -> meetingTestData.getMeeting().addAttendee(meetingTestData.getMeetingGroup(), newMemberId, 6),
				MeetingGuestsNumberIsAboveLimitRule.class);
	}

	@Test
	public void addAttendee_WhenAttendeeLimitIsReached_IsNotPossible() {
		var creatorId = new MemberId(UUID.randomUUID());
		var meetingTestData = createMeetingTestData(new MeetingTestDataOptionsBuilder()
				.setCreatorId(creatorId)
				.setRvspTerm(new Term(SystemClock.now().minusHours(1), SystemClock.now().plusHours(3)))
				.setAttendeesLimit(2)
				.createMeetingTestDataOptions());
		var newMemberId = new MemberId(UUID.randomUUID());
		var aboveLimitMember = new MemberId(UUID.randomUUID());

		meetingTestData.getMeetingGroup().joinToGroupMember(newMemberId);
		meetingTestData.getMeetingGroup().joinToGroupMember(aboveLimitMember);
		meetingTestData.getMeeting().addAttendee(meetingTestData.getMeetingGroup(), newMemberId, 0);

		// reach limit: creator of meeting (automatically) and newMember with 3 guests = 5
		assertBrokenRule(
				() -> meetingTestData.getMeeting().addAttendee(meetingTestData.getMeetingGroup(), aboveLimitMember, 0),
				MeetingAttendeesNumberIsAboveLimitRule.class);
	}
	

}