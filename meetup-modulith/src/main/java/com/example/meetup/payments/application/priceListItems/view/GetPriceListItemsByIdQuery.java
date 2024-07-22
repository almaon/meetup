package com.example.meetup.payments.application.priceListItems.view;


import java.util.UUID;

import com.example.meetup.payments.base.application.IQueryHandler;
import com.example.meetup.payments.base.application.QueryBase;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetPriceListItemsByIdQuery extends QueryBase {

	@Getter
	private final UUID priceListItemId;

	@Override
	public Class<? extends IQueryHandler> getHandlerType() {
		return GetPriceListItemsByIdQueryHandler.class;
	}

}
