package com.example.meetup.meetings.domain.meeting.rules;

import com.example.meetup.meetings.base.domain.IBusinessRule;

public class MeetingAttendeesNumberIsAboveLimitRule implements IBusinessRule {

	private Integer attendeesLimit;

	private int allActiveAttendeesWithGuestsNumber;

	private int guestsNumber;

	public MeetingAttendeesNumberIsAboveLimitRule(Integer attendeesLimit, int allActiveAttendeesWithGuestsNumber,
			int guestsNumber) {
		this.attendeesLimit = attendeesLimit;
		this.allActiveAttendeesWithGuestsNumber = allActiveAttendeesWithGuestsNumber;
		this.guestsNumber = guestsNumber;
	}

	@Override
	public boolean isBroken() {
		return attendeesLimit != null && attendeesLimit > 0 && attendeesLimit < allActiveAttendeesWithGuestsNumber + 1 + guestsNumber;
	}

	@Override
	public String message() {
		return "Meeting attendees number is above limit";
	}

}
