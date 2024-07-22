package com.example.meetup.meetings.domain.meeting.rules;

import java.util.List;

import com.example.meetup.meetings.base.domain.IBusinessRule;
import com.example.meetup.meetings.domain.meeting.MeetingNotAttendee;
import com.example.meetup.meetings.domain.member.MemberId;

public class MemberCannotBeNotAttendeeTwiceRule implements IBusinessRule {

	private List<MeetingNotAttendee> notAttendees;

	private MemberId memberId;

	public MemberCannotBeNotAttendeeTwiceRule(List<MeetingNotAttendee> notAttendees, MemberId memberId) {
		this.notAttendees = notAttendees;
		this.memberId = memberId;
	}

	public boolean isBroken() {
		return notAttendees.stream().anyMatch(x -> x.isActiveNotAttendee(memberId));
	}

	public String message() {
		return "Member cannot be active not attendee twice";
	}

}
