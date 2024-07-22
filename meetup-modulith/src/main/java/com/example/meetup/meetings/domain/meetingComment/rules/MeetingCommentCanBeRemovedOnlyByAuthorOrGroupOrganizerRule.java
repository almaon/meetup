package com.example.meetup.meetings.domain.meetingComment.rules;

import com.example.meetup.meetings.base.domain.IBusinessRule;
import com.example.meetup.meetings.domain.meetingGroup.MeetingGroup;
import com.example.meetup.meetings.domain.member.MemberId;

public class MeetingCommentCanBeRemovedOnlyByAuthorOrGroupOrganizerRule implements IBusinessRule {

	private MeetingGroup meetingGroup;
	private MemberId authorId;
	private MemberId removingMemberId;

	public MeetingCommentCanBeRemovedOnlyByAuthorOrGroupOrganizerRule(MeetingGroup meetingGroup, MemberId authorId,
			MemberId removingMemberId) {
		this.meetingGroup = meetingGroup;
		this.authorId = authorId;
		this.removingMemberId = removingMemberId;
	}

	@Override
	public boolean isBroken() {
		return removingMemberId.notEquals(authorId) && !meetingGroup.isOrganizer(removingMemberId);
	}

	@Override
	public String message() {
		return "Only author of comment or group organizer can remove meeting comment.";
	}
}
