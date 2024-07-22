package com.example.meetup.meetings.application.members.view;


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
public class MemberView {
	
	@Id
	@Column(name = "id", length = 16, unique = true, nullable = false)
	private UUID memberId;

	private String email;
	private String firstName;
	private LocalDateTime registerDate;
	private String lastName;
	private String login;
	
}