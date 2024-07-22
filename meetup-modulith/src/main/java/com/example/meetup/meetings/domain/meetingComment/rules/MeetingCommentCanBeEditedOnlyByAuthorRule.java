package com.example.meetup.meetings.domain.meetingComment.rules;

import com.example.meetup.meetings.base.domain.IBusinessRule;
import com.example.meetup.meetings.domain.member.MemberId;

public class MeetingCommentCanBeEditedOnlyByAuthorRule implements IBusinessRule {

	private MemberId authorId;
	private MemberId editorId;

	public MeetingCommentCanBeEditedOnlyByAuthorRule(MemberId authorId, MemberId editorId) {
		this.authorId = authorId;
		this.editorId = editorId;
	}

	@Override
	public boolean isBroken() {
		return editorId.notEquals(authorId);
	}

	@Override
	public String message() {
		return "Only the author of a comment can edit it.";
	}
}
