package com.example.meetup.payments.application.subscriptions.view;


import java.util.UUID;

import com.example.meetup.payments.base.application.IQueryHandler;
import com.example.meetup.payments.base.application.QueryBase;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetSubscriptionsByIdQuery extends QueryBase {

	@Getter
	private final UUID subscriptionId;

	@Override
	public Class<? extends IQueryHandler> getHandlerType() {
		return GetSubscriptionsByIdQueryHandler.class;
	}

}
