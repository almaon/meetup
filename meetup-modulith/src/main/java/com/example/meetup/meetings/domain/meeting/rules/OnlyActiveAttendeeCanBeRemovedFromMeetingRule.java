package com.example.meetup.meetings.domain.meeting.rules;

import java.util.List;

import com.example.meetup.meetings.base.domain.IBusinessRule;
import com.example.meetup.meetings.domain.meeting.MeetingAttendee;
import com.example.meetup.meetings.domain.member.MemberId;

public class OnlyActiveAttendeeCanBeRemovedFromMeetingRule implements IBusinessRule {

	private List<MeetingAttendee> attendees;
	private MemberId attendeeId;

	public OnlyActiveAttendeeCanBeRemovedFromMeetingRule(List<MeetingAttendee> attendees, MemberId attendeeId) {
		this.attendees = attendees;
		this.attendeeId = attendeeId;
	}

	@Override
	public boolean isBroken() {
		return attendees.stream().filter(x -> x.isActiveAttendee(attendeeId)).findAny().isEmpty();
	}

	@Override
	public String message() {
		return "Only active attendee can be removed from meeting";
	}

}
