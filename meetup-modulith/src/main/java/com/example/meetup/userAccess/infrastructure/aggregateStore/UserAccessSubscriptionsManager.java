package com.example.meetup.userAccess.infrastructure.aggregateStore;

import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.example.meetup.userAccess.base.application.IProjector;
import com.example.meetup.userAccess.base.domain.IDomainEvent;
import com.example.meetup.userAccess.base.infrastructure.ISubscriptionsManager;


@Component
public class UserAccessSubscriptionsManager implements ISubscriptionsManager {

	private final List<IProjector> projectors;
	
	public UserAccessSubscriptionsManager(List<IProjector> projectors) {
		this.projectors = projectors;
	}
	
	@Override
	public void addEvent(IDomainEvent event) {
	
		projectors.forEach(projector -> projector.project(event));
	}

}
