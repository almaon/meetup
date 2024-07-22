package com.example.meetup.meetings.domain.meeting.rules;

import com.example.meetup.meetings.base.domain.IBusinessRule;

public class MeetingGuestsLimitCannotBeNegativeRule implements IBusinessRule {

	private int guestsLimit;

	public MeetingGuestsLimitCannotBeNegativeRule(int guestsLimit) {
		this.guestsLimit = guestsLimit;
	}

	@Override
	public boolean isBroken() {
		return guestsLimit < 0;
	}

	@Override
	public String message() {
		return "Guests limit cannot be negative";
	}
}
