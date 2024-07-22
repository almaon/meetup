package com.example.meetup.userAccess.application.users.view;


import java.time.LocalDateTime;
import java.util.UUID;

import com.example.meetup.userAccess.domain.users.UserType;

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
public class UsersView {
	
	@Id
	@Column(name = "id", length = 16, unique = true, nullable = false)
	private UUID userId;

	private String lastName;
	private String email;
	private LocalDateTime registerDate;
	private String login;
	private String firstName;
	private UserType userType;
	
}
