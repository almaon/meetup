package com.example.meetup.meetings.domain.meetingCommentingConfiguration.rules;

import com.example.meetup.meetings.base.domain.IBusinessRule;
import com.example.meetup.meetings.domain.meetingGroup.MeetingGroup;
import com.example.meetup.meetings.domain.member.MemberId;

public class MeetingCommentingCanBeEnabledOnlyByGroupOrganizerRule implements IBusinessRule {

	private MeetingGroup meetingGroup;
	private MemberId enablingMemberId;

	public MeetingCommentingCanBeEnabledOnlyByGroupOrganizerRule(MemberId enablingMemberId, MeetingGroup meetingGroup) {
		this.meetingGroup = meetingGroup;
		this.enablingMemberId = enablingMemberId;
	}

	@Override
	public boolean isBroken() {
		return !meetingGroup.isOrganizer(enablingMemberId);
	}

	@Override
	public String message() {
		return "Commenting for meeting can be enabled only by group organizer";
	}
}
