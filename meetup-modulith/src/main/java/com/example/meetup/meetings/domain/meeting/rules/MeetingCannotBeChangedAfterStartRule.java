package com.example.meetup.meetings.domain.meeting.rules;

import com.example.meetup.meetings.base.domain.IBusinessRule;
import com.example.meetup.meetings.domain.meeting.MeetingTerm;

public class MeetingCannotBeChangedAfterStartRule implements IBusinessRule {

	private MeetingTerm meetingTerm;

	public MeetingCannotBeChangedAfterStartRule(MeetingTerm meetingTerm) {
		this.meetingTerm = meetingTerm;
	}

	@Override
	public boolean isBroken() {
		return meetingTerm.isAfterStart();
	}

	@Override
	public String message() {
		return "Meeting cannot be changed after start";
	}

}
