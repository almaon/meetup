package com.example.meetup.payments.application.payers.view;

import com.example.meetup.payments.base.application.IQueryHandler;
import com.example.meetup.payments.base.application.QueryBase;


public class GetAllPayerQuery extends QueryBase {

	@Override
	public Class<? extends IQueryHandler> getHandlerType() {
		return GetAllPayerQueryHandler.class;
	}

}
