package com.example.meetup.meetings.domain.meeting.rules;

import com.example.meetup.meetings.base.domain.IBusinessRule;

public class MeetingAttendeesLimitCannotBeNegativeRule implements IBusinessRule {

	private Integer attendeesLimit;

	public MeetingAttendeesLimitCannotBeNegativeRule(Integer attendeesLimit) {
		this.attendeesLimit = attendeesLimit;
	}

	@Override
	public boolean isBroken() {
		return attendeesLimit != null && attendeesLimit < 0;
	}

	@Override
	public String message() {
		return "Attendees limit cannot be negative";
	}
}
