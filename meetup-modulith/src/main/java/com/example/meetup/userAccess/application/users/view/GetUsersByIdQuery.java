package com.example.meetup.userAccess.application.users.view;


import java.util.UUID;

import com.example.meetup.userAccess.base.application.IQueryHandler;
import com.example.meetup.userAccess.base.application.QueryBase;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetUsersByIdQuery extends QueryBase {

	@Getter
	private final UUID userId;

	@Override
	public Class<? extends IQueryHandler> getHandlerType() {
		return GetUsersByIdQueryHandler.class;
	}

}
