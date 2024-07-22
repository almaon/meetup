package com.example.meetup.meetings.base.application;

import java.time.LocalDateTime;
import java.util.UUID;

import com.example.meetup.meetings.base.SystemClock;

import lombok.Getter;


public class ApplicationEventBase implements IApplicationEvent {

	@Getter
	private UUID id;

	private LocalDateTime occurredOn;

	public ApplicationEventBase() {
		id = UUID.randomUUID();
		occurredOn = SystemClock.now();
	}

	@Override
	public LocalDateTime occuredOn() {
		return occurredOn;
	}
}
