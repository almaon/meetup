package com.example.meetup.payments.domain.subscriptionRenewalPayments;

import com.example.meetup.payments.base.domain.TypedIdValueBase;

import java.util.UUID;


public class SubscriptionRenewalPaymentId extends TypedIdValueBase<UUID> {

	public SubscriptionRenewalPaymentId(UUID value) {
		super(value);
	}

}
