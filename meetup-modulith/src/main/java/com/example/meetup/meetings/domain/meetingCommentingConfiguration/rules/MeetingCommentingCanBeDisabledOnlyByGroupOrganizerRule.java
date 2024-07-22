package com.example.meetup.meetings.domain.meetingCommentingConfiguration.rules;

import com.example.meetup.meetings.base.domain.IBusinessRule;
import com.example.meetup.meetings.domain.meetingGroup.MeetingGroup;
import com.example.meetup.meetings.domain.member.MemberId;

public class MeetingCommentingCanBeDisabledOnlyByGroupOrganizerRule implements IBusinessRule {

	private MeetingGroup meetingGroup;
	private MemberId disablingMemberId;

	public MeetingCommentingCanBeDisabledOnlyByGroupOrganizerRule(MemberId disablingMemberId,
			MeetingGroup meetingGroup) {
		this.meetingGroup = meetingGroup;
		this.disablingMemberId = disablingMemberId;
	}

	@Override
	public boolean isBroken() {
		return !meetingGroup.isOrganizer(disablingMemberId);
	}

	@Override
	public String message() {
		return "Commenting for meeting can be disabled only by group organizer";
	}
}
