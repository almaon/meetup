package com.example.meetup.payments.application.meetingFees.view;

import com.example.meetup.payments.base.application.IQueryHandler;
import com.example.meetup.payments.base.application.QueryBase;


public class GetAllMeetingFeesQuery extends QueryBase {

	@Override
	public Class<? extends IQueryHandler> getHandlerType() {
		return GetAllMeetingFeesQueryHandler.class;
	}

}
