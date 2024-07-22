package com.example.meetup.meetings.application.meetingCommentingConfigurations.view;


import java.util.UUID;

import com.example.meetup.meetings.base.application.IQueryHandler;
import com.example.meetup.meetings.base.application.QueryBase;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetMeetingCommentingConfigurationsByIdQuery extends QueryBase {

	@Getter
	private final UUID meetingId;

	@Override
	public Class<? extends IQueryHandler> getHandlerType() {
		return GetMeetingCommentingConfigurationsByIdQueryHandler.class;
	}

}
