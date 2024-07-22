package com.example.meetup.userAccess.application.confirmationLinks.view;

import com.example.meetup.userAccess.base.application.IQueryHandler;
import com.example.meetup.userAccess.base.application.QueryBase;


public class GetAllConfirmationLinksQuery extends QueryBase {

	@Override
	public Class<? extends IQueryHandler> getHandlerType() {
		return GetAllConfirmationLinksQueryHandler.class;
	}

}
