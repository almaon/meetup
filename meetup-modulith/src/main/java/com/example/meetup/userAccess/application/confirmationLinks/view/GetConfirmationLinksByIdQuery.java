package com.example.meetup.userAccess.application.confirmationLinks.view;



import com.example.meetup.userAccess.base.application.IQueryHandler;
import com.example.meetup.userAccess.base.application.QueryBase;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetConfirmationLinksByIdQuery extends QueryBase {

	@Getter
	private final String confirmationLink;

	@Override
	public Class<? extends IQueryHandler> getHandlerType() {
		return GetConfirmationLinksByIdQueryHandler.class;
	}

}
