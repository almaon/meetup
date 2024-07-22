package com.example.meetup.payments.domain.payers;

import com.example.meetup.payments.base.domain.TypedIdValueBase;

import java.util.UUID;


public class PayerId extends TypedIdValueBase<UUID> {

	public PayerId(UUID value) {
		super(value);
	}

}
