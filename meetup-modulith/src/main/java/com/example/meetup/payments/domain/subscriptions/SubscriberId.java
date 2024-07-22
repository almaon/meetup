package com.example.meetup.payments.domain.subscriptions;

import com.example.meetup.payments.base.domain.TypedIdValueBase;

import java.util.UUID;


public class SubscriberId extends TypedIdValueBase<UUID> {

	public SubscriberId(UUID value) {
		super(value);
	}

}
