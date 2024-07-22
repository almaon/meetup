package com.example.meetup.meetings.application.integration.listen.userCreated;

import com.example.meetup.meetings.base.domain.DomainEventBase;


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
public class UserCreatedMeetingsIntegrationEvent extends DomainEventBase {


	private UUID userId;
	private String firstName;
	private LocalDateTime registerDate;
	private String lastName;
	private String login;
	private String email;
	private UserType userType;
	
}
