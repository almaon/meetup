package com.example.meetup.meetings.application.meetings.view;


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
public class MeetingsView {
	
	@Id
	@Column(name = "id", length = 16, unique = true, nullable = false)
	private UUID meetingId;

	private String title;
	private String description;
	private String meetingLocationPostalCode;
	private String meetingLocationAddress;
	private String meetingLocationCity;
	private LocalDateTime termStartDate;
	private LocalDateTime termEndDate;
	private UUID meetingGroupId;
	private String meetingGroupName;
	private Boolean isCanceled;
	
}
