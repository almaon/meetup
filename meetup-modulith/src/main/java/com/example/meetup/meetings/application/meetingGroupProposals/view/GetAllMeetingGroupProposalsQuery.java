package com.example.meetup.meetings.application.meetingGroupProposals.view;

import com.example.meetup.meetings.base.application.IQueryHandler;
import com.example.meetup.meetings.base.application.QueryBase;


public class GetAllMeetingGroupProposalsQuery extends QueryBase {

	@Override
	public Class<? extends IQueryHandler> getHandlerType() {
		return GetAllMeetingGroupProposalsQueryHandler.class;
	}

}
