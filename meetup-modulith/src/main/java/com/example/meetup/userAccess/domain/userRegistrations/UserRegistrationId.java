package com.example.meetup.userAccess.domain.userRegistrations;

import com.example.meetup.userAccess.base.domain.TypedIdValueBase;

import java.util.UUID;


public class UserRegistrationId extends TypedIdValueBase<UUID> {

	public UserRegistrationId(UUID value) {
		super(value);
	}

}
