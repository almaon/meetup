package com.example.meetup.meetings.domain.meeting;

import com.example.meetup.meetings.base.domain.TypedIdValueBase;

import java.util.UUID;


public class MeetingId extends TypedIdValueBase<UUID> {

	public MeetingId(UUID value) {
		super(value);
	}

}
