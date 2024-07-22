package com.example.meetup.administration.application.administrators.view;


import java.util.UUID;

import com.example.meetup.administration.base.application.IQueryHandler;
import com.example.meetup.administration.base.application.QueryBase;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetAdministratorsByLoginQuery extends QueryBase {

	@Getter
	private final String login;

	@Override
	public Class<? extends IQueryHandler> getHandlerType() {
		return GetAdministratorsByIdQueryHandler.class;
	}

}
