package com.example.meetup.payments.application.priceListItems.view;

import com.example.meetup.payments.base.application.IQueryHandler;
import com.example.meetup.payments.base.application.QueryBase;


public class GetAllPriceListItemsQuery extends QueryBase {

	@Override
	public Class<? extends IQueryHandler> getHandlerType() {
		return GetAllPriceListItemsQueryHandler.class;
	}

}
