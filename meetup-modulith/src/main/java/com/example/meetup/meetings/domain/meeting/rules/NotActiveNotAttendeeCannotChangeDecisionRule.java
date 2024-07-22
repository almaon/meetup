package com.example.meetup.meetings.domain.meeting.rules;

import java.util.List;

import com.example.meetup.meetings.base.domain.IBusinessRule;
import com.example.meetup.meetings.domain.meeting.MeetingNotAttendee;
import com.example.meetup.meetings.domain.member.MemberId;

public class NotActiveNotAttendeeCannotChangeDecisionRule implements IBusinessRule {

	private List<MeetingNotAttendee> notAttendees;

	private MemberId memberId;

	public NotActiveNotAttendeeCannotChangeDecisionRule(List<MeetingNotAttendee> notAttendees, MemberId memberId) {
		this.notAttendees = notAttendees;
		this.memberId = memberId;
	}

	@Override
	public boolean isBroken() {
		return notAttendees.stream().filter(x -> x.isActiveNotAttendee(memberId)).findAny().isEmpty();
	}

	@Override
	public String message() {
		return "Member is not active not attendee";
	}

}
