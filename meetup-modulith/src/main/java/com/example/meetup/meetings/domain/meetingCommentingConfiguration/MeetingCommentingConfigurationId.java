package com.example.meetup.meetings.domain.meetingCommentingConfiguration;

import com.example.meetup.meetings.base.domain.TypedIdValueBase;

import java.util.UUID;


public class MeetingCommentingConfigurationId extends TypedIdValueBase<UUID> {

	public MeetingCommentingConfigurationId(UUID value) {
		super(value);
	}

}
