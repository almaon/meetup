package com.example.meetup.userAccess.domain.userRegistrations.events;


import java.time.LocalDateTime;
import java.util.UUID;

import com.example.meetup.userAccess.base.domain.DomainEventBase;
import com.example.meetup.userAccess.domain.userRegistrations.UserRegistrationStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter


@AllArgsConstructor
@NoArgsConstructor
public class NewUserRegisteredEvent extends DomainEventBase {


	private String lastName;
	private String email;
	private UUID userRegistrationId;
	private String login;
	private String firstName;
	private String confirmLink;
	private LocalDateTime registerDate;
	private String encodedPassword;
	private UserRegistrationStatus status;
	
}
