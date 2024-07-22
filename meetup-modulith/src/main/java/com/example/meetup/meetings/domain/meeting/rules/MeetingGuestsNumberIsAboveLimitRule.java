package com.example.meetup.meetings.domain.meeting.rules;

import com.example.meetup.meetings.base.domain.IBusinessRule;

public class MeetingGuestsNumberIsAboveLimitRule implements IBusinessRule {

	private int guestsNumber;

	private int guestsLimit;

	public MeetingGuestsNumberIsAboveLimitRule(int guestsLimit, int guestsNumber) {
		this.guestsNumber = guestsNumber;
		this.guestsLimit = guestsLimit;
	}

	@Override
	public boolean isBroken() {
		return guestsLimit > 0 && guestsLimit < guestsNumber;
	}

	@Override
	public String message() {
		return "Meeting guests number is above limit";
	}

}
