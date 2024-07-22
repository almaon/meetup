package com.example.meetup.meetings.domain.member;

import com.example.meetup.meetings.base.domain.TypedIdValueBase;

import java.util.UUID;


public class MemberId extends TypedIdValueBase<UUID> {

	public MemberId(UUID value) {
		super(value);
	}

}
