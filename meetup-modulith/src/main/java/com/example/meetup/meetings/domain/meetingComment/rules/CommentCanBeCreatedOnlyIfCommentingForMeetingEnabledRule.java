package com.example.meetup.meetings.domain.meetingComment.rules;

import com.example.meetup.meetings.base.domain.IBusinessRule;
import com.example.meetup.meetings.domain.meetingCommentingConfiguration.MeetingCommentingConfiguration;

public class CommentCanBeCreatedOnlyIfCommentingForMeetingEnabledRule implements IBusinessRule {

	private MeetingCommentingConfiguration meetingCommentingConfiguration;

	public CommentCanBeCreatedOnlyIfCommentingForMeetingEnabledRule(
			MeetingCommentingConfiguration meetingCommentingConfiguration) {
		this.meetingCommentingConfiguration = meetingCommentingConfiguration;
	}

	@Override
	public boolean isBroken() {
		return !meetingCommentingConfiguration.getIsCommentingEnabled();
	}

	@Override
	public String message() {
		return "Commenting for meeting is disabled.";
	}
}
