package com.example.meetup.administration.application.administrators.view;

import com.example.meetup.administration.base.application.IQueryHandler;
import com.example.meetup.administration.base.application.QueryBase;

import java.util.List;

import java.util.UUID;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetAllAdministratorsByIdsQuery extends QueryBase {

	@Getter
	private final List<UUID> ids;

	@Override
	public Class<? extends IQueryHandler> getHandlerType() {
		return GetAllAdministratorsByIdsQueryHandler.class;
	}

}
