package com.example.meetup.meetings.domain.meetingGroupProposal.events;


import com.example.meetup.meetings.base.domain.DomainEventBase;

import java.util.UUID;
	
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter


@AllArgsConstructor
@NoArgsConstructor
public class MeetingGroupProposalRejectedEvent extends DomainEventBase {


	private UUID meetingGroupProposalId;
	private String rejectReason;
	
}
