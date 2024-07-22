package com.example.meetup.meetings.infrastructure.aggregateStore;

import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.example.meetup.meetings.base.application.IProjector;
import com.example.meetup.meetings.base.domain.IDomainEvent;
import com.example.meetup.meetings.base.infrastructure.ISubscriptionsManager;


@Component
public class MeetingsSubscriptionsManager implements ISubscriptionsManager {

	private final List<IProjector> projectors;
	
	public MeetingsSubscriptionsManager(List<IProjector> projectors) {
		this.projectors = projectors;
	}
	
	@Override
	public void addEvent(IDomainEvent event) {
	
		projectors.forEach(projector -> {
			projector.project(event);
		});
	}

}
