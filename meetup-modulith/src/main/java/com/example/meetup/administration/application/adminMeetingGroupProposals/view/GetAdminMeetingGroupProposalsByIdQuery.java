package com.example.meetup.administration.application.adminMeetingGroupProposals.view;

import java.util.UUID;

import com.example.meetup.administration.base.application.IQueryHandler;
import com.example.meetup.administration.base.application.QueryBase;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetAdminMeetingGroupProposalsByIdQuery extends QueryBase {

	@Getter
	private final UUID meetingGroupProposalId;

	@Override
	public Class<? extends IQueryHandler> getHandlerType() {
		return GetAdminMeetingGroupProposalsByIdQueryHandler.class;
	}

}
