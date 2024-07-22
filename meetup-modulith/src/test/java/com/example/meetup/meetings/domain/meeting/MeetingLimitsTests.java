package com.example.meetup.meetings.domain.meeting;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

import com.example.meetup.meetings.domain.meeting.MeetingLimits;
import com.example.meetup.meetings.domain.meeting.rules.MeetingAttendeesLimitCannotBeNegativeRule;
import com.example.meetup.meetings.domain.meeting.rules.MeetingAttendeesLimitMustBeGreaterThanGuestsLimitRule;
import com.example.meetup.meetings.domain.meeting.rules.MeetingGuestsLimitCannotBeNegativeRule;

public class MeetingLimitsTests extends MeetingTestBase {

	@Test
	public void createMeetingLimits_whenAttendeesLimitIsGreaterThanGuestsLimit_isSuccessful() {
		var meetingLimits = new MeetingLimits(15, 5);

		assertThat(meetingLimits.getAttendeesLimit(), equalTo(15));
		assertThat(meetingLimits.getGuestsLimit(), equalTo(5));
	}

	@Test
	public void createMeetingLimits_whenAttendeesLimitIsLessThanGuestsLimit_breaksMeetingAttendeesLimitMustBeGreaterThanGuestsLimitRule() {
		assertBrokenRule(() -> {
			new MeetingLimits(5, 8);
		}, MeetingAttendeesLimitMustBeGreaterThanGuestsLimitRule.class);
	}

	@Test
	public void createMeetingLimits_WhenAttendeesLimitIsNotDefined_GuestsLimitCanBeAny() {
		var meetingLimits = new MeetingLimits(null, 5);

		assertThat(meetingLimits.getAttendeesLimit(), equalTo(null));
		assertThat(meetingLimits.getGuestsLimit(), equalTo(5));
	}

	@Test
	public void CreateMeetingLimits_WhenAttendeesLimitIsNegative_BreaksMeetingAttendeesLimitCannotBeNegativeRule() {
		assertBrokenRule(() -> {
			new MeetingLimits(-2, 8);
		}, MeetingAttendeesLimitCannotBeNegativeRule.class);
	}

	@Test
	public void createMeetingLimits_WhenGuestsLimitIsNegative_BreaksMeetingGuestsLimitCannotBeNegativeRule() {
		assertBrokenRule(() -> {
			new MeetingLimits(20, -9);
		}, MeetingGuestsLimitCannotBeNegativeRule.class);
	}
}