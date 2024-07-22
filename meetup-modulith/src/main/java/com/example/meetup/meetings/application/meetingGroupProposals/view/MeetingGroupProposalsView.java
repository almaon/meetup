package com.example.meetup.meetings.application.meetingGroupProposals.view;


import jakarta.persistence.Column;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MeetingGroupProposalsView {
	
	@Id
	@Column(name = "id", length = 16, unique = true, nullable = false)
	private UUID meetingGroupProposalId;

	private String name;
	private String description;
	private String locationCountryCode;
	private String locationCity;
	private UUID proposalUserId;
	private LocalDateTime proposalDate;
	private String status;
	private String rejectReason;
	
}
