package com.example.meetup.payments.domain.subscriptionPayments;

import java.util.UUID;

import com.example.meetup.payments.base.domain.TypedIdValueBase;


public class SubscriptionPaymentId extends TypedIdValueBase<UUID> {

	public SubscriptionPaymentId(UUID value) {
		super(value);
	}

}
