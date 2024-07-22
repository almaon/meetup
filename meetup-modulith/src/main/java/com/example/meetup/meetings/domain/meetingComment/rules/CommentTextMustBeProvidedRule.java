package com.example.meetup.meetings.domain.meetingComment.rules;

import com.example.meetup.meetings.base.domain.IBusinessRule;

public class CommentTextMustBeProvidedRule implements IBusinessRule {

	private String comment;

	public CommentTextMustBeProvidedRule(String comment) {
		this.comment = comment;
	}

	public boolean isBroken() {
		return comment == null || comment.equals("");
	}

	public String message() {
		return "Comment text must be provided.";
	}
}
