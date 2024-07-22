package com.example.meetup.meetings.domain.meetingComment.rules;

import com.example.meetup.meetings.base.domain.IBusinessRule;
import com.example.meetup.meetings.domain.meetingCommentingConfiguration.MeetingCommentingConfiguration;

public class CommentCanBeEditedOnlyIfCommentingForMeetingEnabledRule implements IBusinessRule {

	private MeetingCommentingConfiguration meetingCommentingConfiguration;

	public CommentCanBeEditedOnlyIfCommentingForMeetingEnabledRule(
			MeetingCommentingConfiguration meetingCommentingConfiguration) {
		this.meetingCommentingConfiguration = meetingCommentingConfiguration;
	}

	public boolean isBroken() {
		return !meetingCommentingConfiguration.getIsCommentingEnabled();
	}

	public String message() {
		return "Commenting for meeting is disabled.";
	}
}
