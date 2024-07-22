package com.example.meetup.meetings.application.meetingGroupProposals.view;


import java.util.UUID;

import com.example.meetup.meetings.base.application.IQueryHandler;
import com.example.meetup.meetings.base.application.QueryBase;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetMeetingGroupProposalsByIdQuery extends QueryBase {

	@Getter
	private final UUID meetingGroupProposalId;

	@Override
	public Class<? extends IQueryHandler> getHandlerType() {
		return GetMeetingGroupProposalsByIdQueryHandler.class;
	}

}
