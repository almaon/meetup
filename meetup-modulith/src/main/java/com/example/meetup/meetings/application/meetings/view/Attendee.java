package com.example.meetup.meetings.application.meetings.view;

import java.util.UUID;


import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Attendee {


	@Id
	@Column(name = "id", length = 16, unique = true, nullable = false)

	
	private UUID memberId;
	private String name;
	private String lastName;
	private String firstName;
	private String email;
	private int gustNumber;
	private Boolean isFeePaid;

}
