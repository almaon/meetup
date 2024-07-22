package com.example.meetup.meetings.application.meetingGroups.view;

import com.example.meetup.meetings.base.application.IQueryHandler;
import com.example.meetup.meetings.base.application.QueryBase;

public class GetAllMeetingGroupsQuery extends QueryBase {

	@Override
	public Class<? extends IQueryHandler> getHandlerType() {
		return GetAllMeetingGroupsQueryHandler.class;
	}

}
