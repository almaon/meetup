package com.example.meetup.meetings.domain.meetingMemberCommentLike;

import com.example.meetup.meetings.base.domain.TypedIdValueBase;

import java.util.UUID;


public class MeetingMemberCommentLikeId extends TypedIdValueBase<UUID> {

	public MeetingMemberCommentLikeId(UUID value) {
		super(value);
	}

}
