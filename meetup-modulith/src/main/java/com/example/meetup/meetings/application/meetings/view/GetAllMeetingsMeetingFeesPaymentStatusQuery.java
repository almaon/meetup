package com.example.meetup.meetings.application.meetings.view;

import com.example.meetup.meetings.base.application.IQueryHandler;
import com.example.meetup.meetings.base.application.QueryBase;


public class GetAllMeetingsMeetingFeesPaymentStatusQuery extends QueryBase {

	@Override
	public Class<? extends IQueryHandler> getHandlerType() {
		return GetAllMeetingsMeetingFeesPaymentStatusQueryHandler.class;
	}

}
