package com.example.meetup.meetings.domain.meeting.rules;

import java.util.List;

import com.example.meetup.meetings.base.domain.IBusinessRule;
import com.example.meetup.meetings.domain.meeting.MeetingAttendee;
import com.example.meetup.meetings.domain.member.MemberId;

public class OnlyMeetingAttendeeCanHaveChangedRoleRule implements IBusinessRule {

	private List<MeetingAttendee> attendees;

	private MemberId newOrganizerId;

	public OnlyMeetingAttendeeCanHaveChangedRoleRule(List<MeetingAttendee> attendees, MemberId newOrganizerId) {
		this.attendees = attendees;
		this.newOrganizerId = newOrganizerId;
	}

	@Override
	public boolean isBroken() {
		return attendees.stream().filter(x -> x.isActiveAttendee(newOrganizerId)).findAny().isEmpty();
	}

	@Override
	public String message() {
		return "Only meeting attendee can be se as organizer of meeting";
	}

}
