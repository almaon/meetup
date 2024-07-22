package com.example.meetup.userAccess.application.confirmationLinks.view;



import com.example.meetup.userAccess.base.application.IQueryHandler;
import com.example.meetup.userAccess.base.application.QueryBase;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetConfirmationLinksByLoginQuery extends QueryBase {

	@Getter
	private final String login;

	@Override
	public Class<? extends IQueryHandler> getHandlerType() {
		return GetConfirmationLinksByLoginQueryHandler.class;
	}

}
