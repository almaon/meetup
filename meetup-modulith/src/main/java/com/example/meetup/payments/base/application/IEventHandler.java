package com.example.meetup.payments.base.application;

public interface IEventHandler<E extends IEvent> {

	Class<? extends IEvent> registeredFor();
	
	void handle(E event);
	
}
