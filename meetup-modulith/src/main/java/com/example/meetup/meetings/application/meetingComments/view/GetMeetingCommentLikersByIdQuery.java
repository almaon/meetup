package com.example.meetup.meetings.application.meetingComments.view;


import java.util.UUID;

import com.example.meetup.meetings.base.application.IQueryHandler;
import com.example.meetup.meetings.base.application.QueryBase;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetMeetingCommentLikersByIdQuery extends QueryBase {

	@Getter
	private final UUID meetingCommentId;

	@Override
	public Class<? extends IQueryHandler> getHandlerType() {
		return GetMeetingCommentLikersByIdQueryHandler.class;
	}

}
