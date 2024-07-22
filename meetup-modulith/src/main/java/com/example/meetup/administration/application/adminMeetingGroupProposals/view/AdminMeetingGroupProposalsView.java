package com.example.meetup.administration.application.adminMeetingGroupProposals.view;


import java.time.LocalDateTime;
import java.util.UUID;
import com.example.meetup.administration.domain.meetingGroupProposals.MeetingGroupProposalStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AdminMeetingGroupProposalsView {
	
	@Id
	@Column(name = "id", length = 16, unique = true, nullable = false)
	private UUID meetingGroupProposalId;

	private String description;
	private String name;
	private UUID proposalUserId;
	private String locationCity;
	private String locationCountryCode;
	private LocalDateTime proposalDate;
	private LocalDateTime decisionDate;
	private String decisionRejectReason;
	private MeetingGroupProposalStatus status;
	private UUID deciderId;
	
}
