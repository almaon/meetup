package com.example.meetup.administration.infrastructure.aggregateStore;

import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.example.meetup.administration.base.application.IProjector;
import com.example.meetup.administration.base.domain.IDomainEvent;
import com.example.meetup.administration.base.infrastructure.ISubscriptionsManager;


@Component
public class AdministrationSubscriptionsManager implements ISubscriptionsManager {

	private final List<IProjector> projectors;
	
	public AdministrationSubscriptionsManager(List<IProjector> projectors) {
		this.projectors = projectors;
	}
	
	@Override
	public void addEvent(IDomainEvent event) {
	
		projectors.forEach(projector -> projector.project(event));
	}

}
