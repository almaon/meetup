package com.example.meetup.meetings.infrastructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import com.example.meetup.meetings.base.application.IEventHandler;
import com.example.meetup.meetings.base.application.IEvent;

@Component
public class MeetingsEventDispatcher {
	
	Map<Class<? extends IEvent>, List<IEventHandler>> eventHandlersMap;

	public MeetingsEventDispatcher(List<IEventHandler> eventHandlers) {
		eventHandlersMap = new HashMap<>();

		for (var handler : eventHandlers) {
			var eventType = handler.registeredFor();

			if (!eventHandlersMap.containsKey(eventType))
				eventHandlersMap.put(eventType, new ArrayList<>());
			
			eventHandlersMap.get(eventType).add(handler);
		}
	}

	@TransactionalEventListener
//	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Async
	protected void handleDomainEvent(IEvent event) {
		if (eventHandlersMap.containsKey(event.getClass())) {
			eventHandlersMap.get(event.getClass()).forEach(handler -> handler.handle(event));
		}
	}
}
