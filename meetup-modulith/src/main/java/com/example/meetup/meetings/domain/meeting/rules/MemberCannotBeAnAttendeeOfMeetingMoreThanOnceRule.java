package com.example.meetup.meetings.domain.meeting.rules;

import java.util.List;

import com.example.meetup.meetings.base.domain.IBusinessRule;
import com.example.meetup.meetings.domain.meeting.MeetingAttendee;
import com.example.meetup.meetings.domain.member.MemberId;

public class MemberCannotBeAnAttendeeOfMeetingMoreThanOnceRule implements IBusinessRule {

	private MemberId attendeeId;

	private List<MeetingAttendee> attendees;

	public MemberCannotBeAnAttendeeOfMeetingMoreThanOnceRule(MemberId attendeeId, List<MeetingAttendee> attendees) {
		this.attendeeId = attendeeId;
		this.attendees = attendees;
	}

	@Override
	public boolean isBroken() {
		return attendees.stream().anyMatch(x -> x.isActiveAttendee(attendeeId));
	}

	@Override
	public String message() {
		return "Member is already an attendee of this meeting";
	}

}
