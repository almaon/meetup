package com.example.meetup.payments.application.payers.view;

import com.example.meetup.payments.base.application.IQueryHandler;
import com.example.meetup.payments.base.application.QueryBase;

import java.util.List;

import java.util.UUID;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetAllPayerByIdsQuery extends QueryBase {

	@Getter
	private final List<UUID> ids;

	@Override
	public Class<? extends IQueryHandler> getHandlerType() {
		return GetAllPayerByIdsQueryHandler.class;
	}

}
