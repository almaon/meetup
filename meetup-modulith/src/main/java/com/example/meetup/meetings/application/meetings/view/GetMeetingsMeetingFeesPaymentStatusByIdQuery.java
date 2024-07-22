package com.example.meetup.meetings.application.meetings.view;


import java.util.UUID;

import com.example.meetup.meetings.base.application.IQueryHandler;
import com.example.meetup.meetings.base.application.QueryBase;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetMeetingsMeetingFeesPaymentStatusByIdQuery extends QueryBase {

	@Getter
	private final UUID meetingId;

	@Override
	public Class<? extends IQueryHandler> getHandlerType() {
		return GetMeetingsMeetingFeesPaymentStatusByIdQueryHandler.class;
	}

}
