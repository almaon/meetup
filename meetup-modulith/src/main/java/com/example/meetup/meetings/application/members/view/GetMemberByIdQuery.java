package com.example.meetup.meetings.application.members.view;


import java.util.UUID;

import com.example.meetup.meetings.base.application.IQueryHandler;
import com.example.meetup.meetings.base.application.QueryBase;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetMemberByIdQuery extends QueryBase {

	@Getter
	private final UUID memberId;

	@Override
	public Class<? extends IQueryHandler> getHandlerType() {
		return GetMemberByIdQueryHandler.class;
	}

}
