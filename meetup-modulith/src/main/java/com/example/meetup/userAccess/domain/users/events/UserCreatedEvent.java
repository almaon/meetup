package com.example.meetup.userAccess.domain.users.events;


import java.time.LocalDateTime;
import java.util.UUID;

import com.example.meetup.userAccess.base.domain.DomainEventBase;
import com.example.meetup.userAccess.domain.users.UserType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter


@AllArgsConstructor
@NoArgsConstructor
public class UserCreatedEvent extends DomainEventBase {


	private UUID userId;
	private String firstName;
	private LocalDateTime registerDate;
	private String lastName;
	private String login;
	private String email;
	private UserType userType;
	
}
