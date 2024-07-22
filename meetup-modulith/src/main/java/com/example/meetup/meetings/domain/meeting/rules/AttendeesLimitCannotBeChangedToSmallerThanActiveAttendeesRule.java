package com.example.meetup.meetings.domain.meeting.rules;

import com.example.meetup.meetings.base.domain.IBusinessRule;
import com.example.meetup.meetings.domain.meeting.MeetingLimits;

public class AttendeesLimitCannotBeChangedToSmallerThanActiveAttendeesRule implements IBusinessRule {
	
	private Integer attendeesLimit;

	private int allActiveAttendeesWithGuestsNumber;

	public AttendeesLimitCannotBeChangedToSmallerThanActiveAttendeesRule(MeetingLimits meetingLimits,
			int allActiveAttendeesWithGuestsNumber) {
		attendeesLimit = meetingLimits.getAttendeesLimit();
		this.allActiveAttendeesWithGuestsNumber = allActiveAttendeesWithGuestsNumber;
	}

	@Override
	public boolean isBroken() {
		return attendeesLimit != null && attendeesLimit.intValue() < allActiveAttendeesWithGuestsNumber;
	}
	
	@Override
	public String message() {
		return "Attendees limit cannot be change to smaller than active attendees number";
	}
}
