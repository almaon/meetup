package com.example.meetup.administration.application.administrators.view;

import com.example.meetup.administration.base.application.IQueryHandler;
import com.example.meetup.administration.base.application.QueryBase;


public class GetAllAdministratorsQuery extends QueryBase {

	@Override
	public Class<? extends IQueryHandler> getHandlerType() {
		return GetAllAdministratorsQueryHandler.class;
	}

}
