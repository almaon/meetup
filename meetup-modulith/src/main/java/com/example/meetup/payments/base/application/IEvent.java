package com.example.meetup.payments.base.application;

import java.time.LocalDateTime;
import java.util.UUID;

public interface IEvent {
	
	UUID getId();
	
	LocalDateTime occuredOn();
	
}
