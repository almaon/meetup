package com.example.meetup.meetings.application.meetingComments.view;

import com.example.meetup.meetings.base.application.IQueryHandler;
import com.example.meetup.meetings.base.application.QueryBase;


public class GetAllMeetingCommentsQuery extends QueryBase {

	@Override
	public Class<? extends IQueryHandler> getHandlerType() {
		return GetAllMeetingCommentsQueryHandler.class;
	}

}
