package com.example.meetup.meetings.application.integration.listen.meetingGroupProposalRejected;

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
public class MeetingGroupProposalRejectedMeetingsIntegrationEvent extends DomainEventBase {


	private UUID meetingGroupProposalId;
	private String decisionRejectReason;
	private LocalDateTime decisionDate;
	private String description;
	private String name;
	private UUID proposalUserId;
	private String locationCity;
	private String locationCountryCode;
	private LocalDateTime proposalDate;
	private UUID adminId;
	
}
