package com.example.meetup.administration.application.adminMeetingGroupProposals.view;

import com.example.meetup.administration.base.application.IQueryHandler;
import com.example.meetup.administration.base.application.QueryBase;


public class GetAllAdminMeetingGroupProposalsQuery extends QueryBase {

	@Override
	public Class<? extends IQueryHandler> getHandlerType() {
		return GetAllAdminMeetingGroupProposalsQueryHandler.class;
	}

}
