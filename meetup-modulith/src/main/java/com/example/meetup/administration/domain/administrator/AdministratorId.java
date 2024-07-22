package com.example.meetup.administration.domain.administrator;

import com.example.meetup.administration.base.domain.TypedIdValueBase;

import java.util.UUID;


public class AdministratorId extends TypedIdValueBase<UUID> {

	public AdministratorId(UUID value) {
		super(value);
	}

}
