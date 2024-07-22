package com.example.meetup.administration.domain.members.events;


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
public class MemberCreatedEvent extends DomainEventBase {


	private UUID memberId;
	private String firstName;
	private String lastName;
	private String email;
	private String login;
	private LocalDateTime registerDate;
	
}
