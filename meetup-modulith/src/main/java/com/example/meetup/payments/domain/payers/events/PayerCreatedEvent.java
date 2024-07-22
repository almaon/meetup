package com.example.meetup.payments.domain.payers.events;


import com.example.meetup.payments.base.domain.DomainEventBase;

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
public class PayerCreatedEvent extends DomainEventBase {


	private UUID payerId;
	private String lastName;
	private String email;
	private String firstName;
	private String login;
	private LocalDateTime registerDate;
	
}
