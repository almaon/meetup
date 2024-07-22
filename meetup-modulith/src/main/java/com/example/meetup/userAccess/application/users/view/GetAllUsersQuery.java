package com.example.meetup.userAccess.application.users.view;

import com.example.meetup.userAccess.base.application.IQueryHandler;
import com.example.meetup.userAccess.base.application.QueryBase;


public class GetAllUsersQuery extends QueryBase {

	@Override
	public Class<? extends IQueryHandler> getHandlerType() {
		return GetAllUsersQueryHandler.class;
	}

}
