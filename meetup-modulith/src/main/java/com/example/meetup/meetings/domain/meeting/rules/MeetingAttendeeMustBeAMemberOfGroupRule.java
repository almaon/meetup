package com.example.meetup.meetings.domain.meeting.rules;

import com.example.meetup.meetings.base.domain.IBusinessRule;
import com.example.meetup.meetings.domain.meetingGroup.MeetingGroup;
import com.example.meetup.meetings.domain.member.MemberId;

public class MeetingAttendeeMustBeAMemberOfGroupRule implements IBusinessRule {

	private MeetingGroup meetingGroup;

	private MemberId attendeeId;

	public MeetingAttendeeMustBeAMemberOfGroupRule(MemberId attendeeId, MeetingGroup meetingGroup) {
		this.attendeeId = attendeeId;
		this.meetingGroup = meetingGroup;
	}

	@Override
	public boolean isBroken() {
		return !meetingGroup.isMemberOfGroup(attendeeId);
	}

	@Override
	public String message() {
		return "Meeting attendee must be a member of group";
	}

}
