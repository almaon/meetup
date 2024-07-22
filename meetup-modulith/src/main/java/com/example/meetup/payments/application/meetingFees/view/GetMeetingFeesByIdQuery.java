package com.example.meetup.payments.application.meetingFees.view;


import java.util.UUID;

import com.example.meetup.payments.base.application.IQueryHandler;
import com.example.meetup.payments.base.application.QueryBase;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetMeetingFeesByIdQuery extends QueryBase {

	@Getter
	private final UUID meetingFeeId;

	@Override
	public Class<? extends IQueryHandler> getHandlerType() {
		return GetMeetingFeesByIdQueryHandler.class;
	}

}
