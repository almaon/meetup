package com.example.meetup.meetings.domain.meetingGroupProposal;

import com.example.meetup.meetings.base.domain.TypedIdValueBase;

import java.util.UUID;


public class MeetingGroupProposalId extends TypedIdValueBase<UUID> {

	public MeetingGroupProposalId(UUID value) {
		super(value);
	}

}
