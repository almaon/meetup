package com.example.meetup.meetings.application.meetingGroups.view;

import com.example.meetup.meetings.base.application.IQueryHandler;
import com.example.meetup.meetings.base.application.QueryBase;

public class GetAllMemberMeetingGroupsQuery extends QueryBase {

	@Override
	public Class<? extends IQueryHandler> getHandlerType() {
		return GetAllMemberMeetingGroupsQueryHandler.class;
	}

}
