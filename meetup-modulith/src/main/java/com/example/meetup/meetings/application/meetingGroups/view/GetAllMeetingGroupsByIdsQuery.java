package com.example.meetup.meetings.application.meetingGroups.view;

import com.example.meetup.meetings.base.application.IQueryHandler;
import com.example.meetup.meetings.base.application.QueryBase;

import java.util.List;

import java.util.UUID;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetAllMeetingGroupsByIdsQuery extends QueryBase {

	@Getter
	private final List<UUID> ids;

	@Override
	public Class<? extends IQueryHandler> getHandlerType() {
		return GetAllMeetingGroupsByIdsQueryHandler.class;
	}

}
