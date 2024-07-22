package com.example.meetup.administration.domain.meetingGroupProposals;

import com.example.meetup.administration.base.domain.TypedIdValueBase;

import java.util.UUID;


public class MeetingGroupProposalId extends TypedIdValueBase<UUID> {

	public MeetingGroupProposalId(UUID value) {
		super(value);
	}

}
