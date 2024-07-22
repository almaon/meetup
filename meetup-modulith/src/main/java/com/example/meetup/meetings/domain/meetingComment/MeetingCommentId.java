package com.example.meetup.meetings.domain.meetingComment;

import com.example.meetup.meetings.base.domain.TypedIdValueBase;

import java.util.UUID;


public class MeetingCommentId extends TypedIdValueBase<UUID> {

	public MeetingCommentId(UUID value) {
		super(value);
	}

}
