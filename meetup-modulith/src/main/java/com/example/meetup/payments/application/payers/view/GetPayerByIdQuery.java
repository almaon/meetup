package com.example.meetup.payments.application.payers.view;


import java.util.UUID;

import com.example.meetup.payments.base.application.IQueryHandler;
import com.example.meetup.payments.base.application.QueryBase;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetPayerByIdQuery extends QueryBase {

	@Getter
	private final UUID payerId;

	@Override
	public Class<? extends IQueryHandler> getHandlerType() {
		return GetPayerByIdQueryHandler.class;
	}

}
