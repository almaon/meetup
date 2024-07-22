package com.example.meetup.payments.domain;

import java.util.UUID;

import com.example.meetup.payments.base.domain.TypedIdValueBase;


public class MeetingId extends TypedIdValueBase<UUID> {

	public MeetingId(UUID value) {
		super(value);
	}

}
