package com.example.meetup.userAccess.application.users.view;

import com.example.meetup.userAccess.base.application.IQueryHandler;
import com.example.meetup.userAccess.base.application.QueryBase;

import java.util.List;

import java.util.UUID;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetAllUsersByIdsQuery extends QueryBase {

	@Getter
	private final List<UUID> ids;

	@Override
	public Class<? extends IQueryHandler> getHandlerType() {
		return GetAllUsersByIdsQueryHandler.class;
	}

}
