package com.example.meetup.meetings.domain.meeting.rules;

import com.example.meetup.meetings.base.domain.IBusinessRule;

public class ReasonOfRemovingAttendeeFromMeetingMustBeProvidedRule implements IBusinessRule {

	private String reason;

	public ReasonOfRemovingAttendeeFromMeetingMustBeProvidedRule(String reason) {
		this.reason = reason;
	}

	@Override
	public boolean isBroken() {
		return reason == null || reason.equals("");
	}

	@Override
	public String message() {
		return "Reason of removing attendee from meeting must be provided";
	}

}
