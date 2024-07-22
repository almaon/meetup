package com.example.meetup.userAccess.application.confirmationLinks.view;

import com.example.meetup.userAccess.base.application.IQueryHandler;
import com.example.meetup.userAccess.base.application.QueryBase;

import java.util.List;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetAllConfirmationLinksByIdsQuery extends QueryBase {

	@Getter
	private final List<String> ids;

	@Override
	public Class<? extends IQueryHandler> getHandlerType() {
		return GetAllConfirmationLinksByIdsQueryHandler.class;
	}

}
