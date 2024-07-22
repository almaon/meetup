package com.example.meetup.userAccess.domain.users;

import com.example.meetup.userAccess.base.domain.TypedIdValueBase;

import java.util.UUID;


public class UserId extends TypedIdValueBase<UUID> {

	public UserId(UUID value) {
		super(value);
	}

}
