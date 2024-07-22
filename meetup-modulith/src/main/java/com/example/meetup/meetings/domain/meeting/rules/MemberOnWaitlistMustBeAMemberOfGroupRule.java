package com.example.meetup.meetings.domain.meeting.rules;

import java.util.List;

import com.example.meetup.meetings.base.domain.IBusinessRule;
import com.example.meetup.meetings.domain.meeting.MeetingAttendee;
import com.example.meetup.meetings.domain.meetingGroup.MeetingGroup;
import com.example.meetup.meetings.domain.member.MemberId;

public class MemberOnWaitlistMustBeAMemberOfGroupRule implements IBusinessRule {

	private MeetingGroup meetingGroup;

	private MemberId memberId;

	private List<MeetingAttendee> attendees;

	public MemberOnWaitlistMustBeAMemberOfGroupRule(MeetingGroup meetingGroup, MemberId memberId,
			List<MeetingAttendee> attendees) {
		this.meetingGroup = meetingGroup;
		this.memberId = memberId;
		this.attendees = attendees;
	}

	@Override
	public boolean isBroken() {
		return !meetingGroup.isMemberOfGroup(memberId);
	}

	@Override
	public String message() {
		return "Member on waitlist must be a member of group";
	}
}
