package com.example.meetup.userAccess.base.domain;

import java.time.LocalDateTime;
import java.util.UUID;
import com.example.meetup.userAccess.base.SystemClock;

import lombok.Getter;


public class DomainEventBase implements IDomainEvent {

	@Getter
	private UUID id;

	private LocalDateTime occurredOn;

	public DomainEventBase() {
		id = UUID.randomUUID();
		occurredOn = SystemClock.now();
	}

	@Override
	public LocalDateTime occuredOn() {
		return occurredOn;
	}
}
