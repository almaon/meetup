package com.example.meetup.meetings.domain.meeting;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.example.meetup.meetings.base.SystemClock;
import com.example.meetup.meetings.domain.meeting.MeetingTerm;
import com.example.meetup.meetings.domain.meeting.Term;
import com.example.meetup.meetings.domain.meeting.events.MeetingAttendeeChangedDecisionEvent;
import com.example.meetup.meetings.domain.meeting.events.MeetingNotAttendeeAddedEvent;
import com.example.meetup.meetings.domain.meeting.events.MeetingNotAttendeeChangedDecisionEvent;
import com.example.meetup.meetings.domain.meeting.rules.MeetingCannotBeChangedAfterStartRule;
import com.example.meetup.meetings.domain.meeting.rules.MemberCannotBeNotAttendeeTwiceRule;
import com.example.meetup.meetings.domain.meeting.rules.NotActiveNotAttendeeCannotChangeDecisionRule;
import com.example.meetup.meetings.domain.member.MemberId;


public class MeetingAddNotAttendeeTests extends MeetingTestBase {

	@Test
	public void addNotAttendee_WhenMeetingHasStarted_IsNotPossible() {
		var creatorId = new MemberId(UUID.randomUUID());
		var meetingTestData = createMeetingTestData(new MeetingTestDataOptionsBuilder()
				.setCreatorId(creatorId)
				.setMeetingTerm(new MeetingTerm(SystemClock.now().minusHours(1), SystemClock.now().plusHours(3)))
				.createMeetingTestDataOptions());

        SystemClock.set(SystemClock.now().plusHours(2));

		assertBrokenRule(() -> meetingTestData.getMeeting().addNotAttendee(creatorId),
				MeetingCannotBeChangedAfterStartRule.class);
	}

	@Test
	public void addNotAttendee_WhenMemberIsAlreadyNotAttendee_IsNotPossible() {
		var creatorId = new MemberId(UUID.randomUUID());
		var meetingTestData = createMeetingTestData(new MeetingTestDataOptionsBuilder()
				.setCreatorId(creatorId)
				.setRvspTerm(new Term(SystemClock.now().minusHours(1), SystemClock.now().plusHours(3)))
				.createMeetingTestDataOptions());
		
        SystemClock.set(SystemClock.now().plusHours(2));

		meetingTestData.getMeeting().addNotAttendee(creatorId);

		assertBrokenRule(() -> meetingTestData.getMeeting().addNotAttendee(creatorId),
				MemberCannotBeNotAttendeeTwiceRule.class);
	}

	@Test
	public void addNotAttendee_WhenMemberIsNotNotAttendee_IsSuccessful() {
		var creatorId = new MemberId(UUID.randomUUID());
		var meetingTestData = createMeetingTestData(new MeetingTestDataOptionsBuilder()
				.setCreatorId(creatorId)
				.createMeetingTestDataOptions());
		var memberId = new MemberId(UUID.randomUUID());

		meetingTestData.getMeeting().addNotAttendee(memberId);

        publishedDomainEvents.addAll(meetingTestData.getMeeting().getDomainEvents());

		var meetingNotAttendeeAdded = assertPublishedDomainEvent(MeetingNotAttendeeAddedEvent.class);

		assertThat(meetingNotAttendeeAdded.getMemberId(), equalTo(memberId.getValue()));
	}

	@Test
	public void addNotAttendee_WhenMemberIsAttendeeAndChangedDecision_IsSuccessful() {
		var creatorId = new MemberId(UUID.randomUUID());
		var meetingTestData = createMeetingTestData(new MeetingTestDataOptionsBuilder()
				.setCreatorId(creatorId)
				.setRvspTerm(new Term(SystemClock.now().minusHours(1), SystemClock.now().plusHours(3)))
				.createMeetingTestDataOptions());
		var newMemberId = new MemberId(UUID.randomUUID());

        SystemClock.set(SystemClock.now().plusHours(2));

		meetingTestData.getMeetingGroup().joinToGroupMember(newMemberId);
		meetingTestData.getMeeting().addAttendee(meetingTestData.getMeetingGroup(), newMemberId, 0);

		meetingTestData.getMeeting().addNotAttendee(newMemberId);

        publishedDomainEvents.addAll(meetingTestData.getMeeting().getDomainEvents());

		var meetingAttendeeChangedDecision = assertPublishedDomainEvent(
				MeetingAttendeeChangedDecisionEvent.class);
		assertThat(meetingAttendeeChangedDecision.getMemberId(), equalTo(newMemberId.getValue()));
	}

	@Test
	public void changeNotAttendeeDecision_WhenMeetingHasStared_IsNotPossible() {
		var creatorId = new MemberId(UUID.randomUUID());
		var meetingTestData = createMeetingTestData(new MeetingTestDataOptionsBuilder()
				.setCreatorId(creatorId)
				.setMeetingTerm(new MeetingTerm(SystemClock.now().minusHours(1), SystemClock.now().plusHours(3)))
				.createMeetingTestDataOptions());
		var newMemberId = new MemberId(UUID.randomUUID());

        SystemClock.set(SystemClock.now().plusHours(2));

		meetingTestData.getMeetingGroup().joinToGroupMember(newMemberId);

		assertBrokenRule(() -> meetingTestData.getMeeting().changeNotAttendeeDecision(newMemberId),
				MeetingCannotBeChangedAfterStartRule.class);
	}

	@Test
	public void changeNotAttendeeDecision_WhenMemberIsNotActiveNotAttendee_IsNotPossible() {
		var creatorId = new MemberId(UUID.randomUUID());
		var meetingTestData = createMeetingTestData(new MeetingTestDataOptionsBuilder()
				.setCreatorId(creatorId)
				.createMeetingTestDataOptions());
		var newMemberId = new MemberId(UUID.randomUUID());

		meetingTestData.getMeetingGroup().joinToGroupMember(newMemberId);

		assertBrokenRule(() -> meetingTestData.getMeeting().changeNotAttendeeDecision(newMemberId),
				NotActiveNotAttendeeCannotChangeDecisionRule.class);
	}

	@Test
	public void changeNotAttendeeDecision_WhenMemberIsNotAttendee_IsSuccessful() {
		var creatorId = new MemberId(UUID.randomUUID());
		var meetingTestData = createMeetingTestData(new MeetingTestDataOptionsBuilder()
				.setCreatorId(creatorId)
				.setRvspTerm(new Term(SystemClock.now().minusHours(1), SystemClock.now().plusHours(3)))
				.createMeetingTestDataOptions());

        SystemClock.set(SystemClock.now().plusHours(2));

		var newMemberId = new MemberId(UUID.randomUUID());

		meetingTestData.getMeetingGroup().joinToGroupMember(newMemberId);
		meetingTestData.getMeeting().addNotAttendee(newMemberId);

		meetingTestData.getMeeting().changeNotAttendeeDecision(newMemberId);

        publishedDomainEvents.addAll(meetingTestData.getMeeting().getDomainEvents());

		var meetingNotAttendeeChangedDecision = assertPublishedDomainEvent(
				MeetingNotAttendeeChangedDecisionEvent.class);
		assertThat(meetingNotAttendeeChangedDecision.getMemberId(), equalTo(newMemberId.getValue()));
	}

}