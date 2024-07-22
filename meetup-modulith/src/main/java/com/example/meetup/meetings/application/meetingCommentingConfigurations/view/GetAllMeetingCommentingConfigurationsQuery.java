package com.example.meetup.meetings.application.meetingCommentingConfigurations.view;

import com.example.meetup.meetings.base.application.IQueryHandler;
import com.example.meetup.meetings.base.application.QueryBase;


public class GetAllMeetingCommentingConfigurationsQuery extends QueryBase {

	@Override
	public Class<? extends IQueryHandler> getHandlerType() {
		return GetAllMeetingCommentingConfigurationsQueryHandler.class;
	}

}
