package com.example.meetup.userAccess.infrastructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.example.meetup.userAccess.base.application.IEventHandler;
import com.example.meetup.userAccess.base.application.IEvent;

@Component
public class UserAccessEventDispatcher {
	
	Map<Class<? extends IEvent>, List<IEventHandler>> eventHandlersMap;

	public UserAccessEventDispatcher(List<IEventHandler> eventHandlers) {
		eventHandlersMap = new HashMap<>();

		for (var handler : eventHandlers) {
			var eventType = handler.registeredFor();

			if (!eventHandlersMap.containsKey(eventType))
				eventHandlersMap.put(eventType, new ArrayList<>());
			
			eventHandlersMap.get(eventType).add(handler);
		}
	}

	@TransactionalEventListener
	@Async
	protected void handleDomainEvent(IEvent event) {
		if (eventHandlersMap.containsKey(event.getClass())) {
			eventHandlersMap.get(event.getClass()).forEach(handler -> handler.handle(event));
		}
	}
}
