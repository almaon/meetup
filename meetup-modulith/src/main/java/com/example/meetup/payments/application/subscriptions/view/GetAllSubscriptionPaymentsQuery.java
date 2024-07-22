package com.example.meetup.payments.application.subscriptions.view;

import com.example.meetup.payments.base.application.IQueryHandler;
import com.example.meetup.payments.base.application.QueryBase;


public class GetAllSubscriptionPaymentsQuery extends QueryBase {

	@Override
	public Class<? extends IQueryHandler> getHandlerType() {
		return GetAllSubscriptionPaymentsQueryHandler.class;
	}

}
