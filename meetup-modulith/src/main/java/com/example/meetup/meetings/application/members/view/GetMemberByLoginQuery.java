package com.example.meetup.meetings.application.members.view;


import java.util.UUID;

import com.example.meetup.meetings.base.application.IQueryHandler;
import com.example.meetup.meetings.base.application.QueryBase;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetMemberByLoginQuery extends QueryBase {

	@Getter
	private final String login;

	@Override
	public Class<? extends IQueryHandler> getHandlerType() {
		return GetMemberByLoginQueryHandler.class;
	}

}
