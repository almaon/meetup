package com.example.meetup.meetings.application.meetingGroups.view;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MeetingGroup {

	@Id
	@Column(name = "id", length = 16, unique = true, nullable = false)
	private UUID id;

	private UUID meetingGroupId;
	private String locationCountryCode;
	private int memberCount;
	private String locationCity;
	private String description;
	private String name;
	private String memberRole;
	private LocalDateTime joinedDate;

}
