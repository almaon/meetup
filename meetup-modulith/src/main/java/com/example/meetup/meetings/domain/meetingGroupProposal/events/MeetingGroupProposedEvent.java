package com.example.meetup.meetings.domain.meetingGroupProposal.events;


import com.example.meetup.meetings.base.domain.DomainEventBase;

import java.time.LocalDateTime;
import java.util.UUID;
	
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter


@AllArgsConstructor
@NoArgsConstructor
public class MeetingGroupProposedEvent extends DomainEventBase {


	private UUID meetingGroupProposalId;
	private String description;
	private String locationCity;
	private String name;
	private String locationCountryCode;
	private UUID proposalUserId;
	private LocalDateTime proposalDate;
	
}
