package com.example.meetup.administration.domain.meetingGroupProposals.events;


import com.example.meetup.administration.base.domain.DomainEventBase;

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
public class MeetingGroupProposalVerificationRequestedEvent extends DomainEventBase {


	private UUID meetingGroupProposalId;
	private String description;
	private String name;
	private UUID proposalUserId;
	private String locationCity;
	private String locationCountryCode;
	private LocalDateTime proposalDate;
	
}
