package com.example.meetup.meetings.domain.meeting.rules;

import com.example.meetup.meetings.base.domain.IBusinessRule;
import com.example.meetup.meetings.domain.meeting.MeetingAttendeeRole;

public class MemberCannotHaveSetAttendeeRoleMoreThanOnceRule implements IBusinessRule {

	private MeetingAttendeeRole meetingAttendeeRole;

	public MemberCannotHaveSetAttendeeRoleMoreThanOnceRule(MeetingAttendeeRole meetingAttendeeRole) {
		this.meetingAttendeeRole = meetingAttendeeRole;
	}

	@Override
	public boolean isBroken() {
		return meetingAttendeeRole == MeetingAttendeeRole.Attendee;
	}

	@Override
	public String message() {
		return "Member cannot be attendee of meeting more than once";
	}

}
