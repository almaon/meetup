package com.example.meetup.administration.domain.members;

import com.example.meetup.administration.base.domain.TypedIdValueBase;

import java.util.UUID;


public class MemberId extends TypedIdValueBase<UUID> {

	public MemberId(UUID value) {
		super(value);
	}

}
