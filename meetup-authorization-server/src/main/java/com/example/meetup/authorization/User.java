package com.example.meetup.authorization;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Table(name = "MeetupUser")
public class User {

	@Id
	@Column(name = "userId", length = 16, unique = true, nullable = false)
	private UUID userId;
	
	private String username;
	
	private String password;

	@ElementCollection(fetch = FetchType.EAGER)
	private List<String> authorities;
}