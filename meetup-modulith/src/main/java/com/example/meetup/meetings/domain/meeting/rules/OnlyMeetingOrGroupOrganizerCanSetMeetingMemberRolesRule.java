package com.example.meetup.meetings.domain.meeting.rules;

import java.util.List;

import com.example.meetup.meetings.base.domain.IBusinessRule;
import com.example.meetup.meetings.domain.meeting.MeetingAttendee;
import com.example.meetup.meetings.domain.meetingGroup.MeetingGroup;
import com.example.meetup.meetings.domain.member.MemberId;

public class OnlyMeetingOrGroupOrganizerCanSetMeetingMemberRolesRule implements IBusinessRule {

	private MemberId settingMemberId;
	private MeetingGroup meetingGroup;
	private List<MeetingAttendee> attendees;

	public OnlyMeetingOrGroupOrganizerCanSetMeetingMemberRolesRule(MemberId settingMemberId, MeetingGroup meetingGroup,
			List<MeetingAttendee> attendees) {
		this.settingMemberId = settingMemberId;
		this.meetingGroup = meetingGroup;
		this.attendees = attendees;
	}

	@Override
	public boolean isBroken() {
		var settingMember = attendees.stream().filter(x -> x.isActiveAttendee(settingMemberId)).findAny().orElse(null);

		var isHost = settingMember != null && settingMember.isActiveHost();
		var isOrganizer = meetingGroup.isOrganizer(settingMemberId);

		return !isHost || !isOrganizer;
	}

	@Override
	public String message() {
		return "Only meeting host or group organizer can set meeting member roles";
	}

}
