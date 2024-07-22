package com.example.meetup.administration.application.integration.listen.userCreated;

import com.example.meetup.administration.base.domain.DomainEventBase;


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
public class UserCreatedAdministrationIntegrationEvent extends DomainEventBase {


	private UUID userId;
	private String firstName;
	private LocalDateTime registerDate;
	private String lastName;
	private String login;
	private String email;
	private UserType userType;
	
}
