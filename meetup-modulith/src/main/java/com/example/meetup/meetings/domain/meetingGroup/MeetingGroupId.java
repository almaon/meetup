package com.example.meetup.meetings.domain.meetingGroup;

import com.example.meetup.meetings.base.domain.TypedIdValueBase;

import java.util.UUID;


public class MeetingGroupId extends TypedIdValueBase<UUID> {

	public MeetingGroupId(UUID value) {
		super(value);
	}

}
