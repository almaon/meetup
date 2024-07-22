package com.example.meetup.administration.domain.administrator.events;


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
public class AdminCreatedEvent extends DomainEventBase {


	private String lastName;
	private String firstName;
	private String email;
	private String login;
	private UUID adminId;
	private LocalDateTime registerDate;
	
}
