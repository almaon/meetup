package com.example.meetup.meetings.domain.meetingComment.rules;

import com.example.meetup.meetings.base.domain.IBusinessRule;
import com.example.meetup.meetings.domain.meetingGroup.MeetingGroup;
import com.example.meetup.meetings.domain.member.MemberId;

public class CommentCanBeAddedOnlyByMeetingGroupMemberRule implements IBusinessRule {

	private MemberId authorId;
	private MeetingGroup meetingGroup;

	public CommentCanBeAddedOnlyByMeetingGroupMemberRule(MemberId authorId, MeetingGroup meetingGroup) {
		this.authorId = authorId;
		this.meetingGroup = meetingGroup;
	}

	@Override
	public boolean isBroken() {
		return !meetingGroup.isMemberOfGroup(authorId);
	}

	@Override
	public String message() {
		return "Only meeting attendee can add comments";
	}
}
