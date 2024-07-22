package com.example.meetup.meetings.domain.meetingComment.rules;

import com.example.meetup.meetings.base.domain.IBusinessRule;
import com.example.meetup.meetings.domain.member.MeetingGroupMemberData;

public class CommentCanBeLikedOnlyByMeetingGroupMemberRule implements IBusinessRule {

	private MeetingGroupMemberData likerMeetingGroupMember;

	public CommentCanBeLikedOnlyByMeetingGroupMemberRule(MeetingGroupMemberData likerMeetingGroupMember) {
		this.likerMeetingGroupMember = likerMeetingGroupMember;
	}

	@Override
	public boolean isBroken() {
		return likerMeetingGroupMember == null;
	}

	@Override
	public String message() {
		return "Comment can be liked only by meeting group member.";
	}
}
