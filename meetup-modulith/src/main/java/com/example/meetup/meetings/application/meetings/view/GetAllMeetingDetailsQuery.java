package com.example.meetup.meetings.application.meetings.view;

import com.example.meetup.meetings.base.application.IQueryHandler;
import com.example.meetup.meetings.base.application.QueryBase;


public class GetAllMeetingDetailsQuery extends QueryBase {

	@Override
	public Class<? extends IQueryHandler> getHandlerType() {
		return GetAllMeetingDetailsQueryHandler.class;
	}

}
