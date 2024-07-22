package com.example.meetup.meetings.domain.meetingGroup.rules;
import com.example.meetup.meetings.base.domain.IBusinessRule;

public class MeetingGroupMustHaveAtLeastOneOrganizerRule implements IBusinessRule {

	private int meetingOrganizerNumber;

	public MeetingGroupMustHaveAtLeastOneOrganizerRule(int meetingOrganizerNumber) {
		this.meetingOrganizerNumber = meetingOrganizerNumber;
	}

	@Override
	public boolean isBroken() {
		return meetingOrganizerNumber == 0;
	}

	@Override
	public String message() {
		return "Meeting group must have at least one organizer";
	}
}