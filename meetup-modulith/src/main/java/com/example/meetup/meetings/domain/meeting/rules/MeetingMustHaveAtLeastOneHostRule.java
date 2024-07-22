package com.example.meetup.meetings.domain.meeting.rules;

import com.example.meetup.meetings.base.domain.IBusinessRule;

public class MeetingMustHaveAtLeastOneHostRule implements IBusinessRule {

	private long meetingHostNumber;

	public MeetingMustHaveAtLeastOneHostRule(long meetingHostNumber) {
		this.meetingHostNumber = meetingHostNumber;
	}

	@Override
	public boolean isBroken() {
		return meetingHostNumber == 0;
	}

	@Override
	public String message() {
		return "Meeting must have at least one host";
	}
}
