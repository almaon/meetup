package com.example.meetup.payments.domain.subscriptions;

import com.example.meetup.payments.base.domain.TypedIdValueBase;

import java.util.UUID;


public class SubscriptionId extends TypedIdValueBase<UUID> {

	public SubscriptionId(UUID value) {
		super(value);
	}

}
