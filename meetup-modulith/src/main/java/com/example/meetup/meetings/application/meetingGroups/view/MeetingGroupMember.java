package com.example.meetup.meetings.application.meetingGroups.view;

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
public class MeetingGroupMember {

	@Id
	@Column(name = "id", length = 16, unique = true, nullable = false)
	private UUID id;

	private UUID memberId;
	private String name;
	private String email;
	private String role;

}
