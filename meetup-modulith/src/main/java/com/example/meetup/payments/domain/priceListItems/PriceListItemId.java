package com.example.meetup.payments.domain.priceListItems;

import com.example.meetup.payments.base.domain.TypedIdValueBase;

import java.util.UUID;


public class PriceListItemId extends TypedIdValueBase<UUID> {

	public PriceListItemId(UUID value) {
		super(value);
	}

}
