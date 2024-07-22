package com.example.meetup.meetings.domain.meetingComment.rules;

import com.example.meetup.meetings.base.domain.IBusinessRule;

public class CommentCannotBeLikedByTheSameMemberMoreThanOnceRule implements IBusinessRule {

	private int memberCommentLikesCount;

	public CommentCannotBeLikedByTheSameMemberMoreThanOnceRule(int memberCommentLikesCount) {
		this.memberCommentLikesCount = memberCommentLikesCount;
	}

	@Override
	public boolean isBroken() {
		return memberCommentLikesCount > 0;
	}

	@Override
	public String message() {
		return "Member cannot like one comment more than once.";
	}
}
