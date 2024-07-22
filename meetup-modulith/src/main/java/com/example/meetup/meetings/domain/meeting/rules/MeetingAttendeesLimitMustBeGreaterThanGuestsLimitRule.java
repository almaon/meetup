package com.example.meetup.meetings.domain.meeting.rules;

import com.example.meetup.meetings.base.domain.IBusinessRule;

public class MeetingAttendeesLimitMustBeGreaterThanGuestsLimitRule implements IBusinessRule {

	private Integer attendeesLimit;

	private int guestsLimit;

	public MeetingAttendeesLimitMustBeGreaterThanGuestsLimitRule(Integer attendeesLimit, int guestsLimit) {
		this.attendeesLimit = attendeesLimit;
		this.guestsLimit = guestsLimit;
	}

	public boolean isBroken() {
		return attendeesLimit != null && attendeesLimit < guestsLimit;
	}

	public String message() {
		return "Attendees limit must be greater than guests limit";
	}

}
