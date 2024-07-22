package com.example.meetup.userAccess.domain.userRegistrations.events;


import com.example.meetup.userAccess.base.domain.DomainEventBase;

import java.time.LocalDateTime;
import java.util.UUID;
	
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter


@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationConfirmedEvent extends DomainEventBase {


	private UUID userRegistrationId;
	private LocalDateTime registerDate;
	private String login;
	private String lastName;
	private String firstName;
	private String email;
	private String encodedPassword;
	
}
