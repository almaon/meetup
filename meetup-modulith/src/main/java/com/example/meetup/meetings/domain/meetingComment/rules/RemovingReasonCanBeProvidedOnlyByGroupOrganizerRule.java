package com.example.meetup.meetings.domain.meetingComment.rules;

import com.example.meetup.meetings.base.domain.IBusinessRule;
import com.example.meetup.meetings.domain.meetingGroup.MeetingGroup;
import com.example.meetup.meetings.domain.member.MemberId;

public class RemovingReasonCanBeProvidedOnlyByGroupOrganizerRule implements IBusinessRule {

	private MeetingGroup meetingGroup;
	private MemberId removingMemberId;
	private String removingReason;

	public RemovingReasonCanBeProvidedOnlyByGroupOrganizerRule(MeetingGroup meetingGroup, MemberId removingMemberId,
			String removingReason) {
		this.meetingGroup = meetingGroup;
		this.removingMemberId = removingMemberId;
		this.removingReason = removingReason;
	}

	@Override
	public boolean isBroken() {
		return !(removingReason == null || removingReason.equals("")) && !meetingGroup.isOrganizer(removingMemberId);
	}

	@Override
	public String message() {
		return "Only group organizer can provide comment's removing reason.";
	}
}
