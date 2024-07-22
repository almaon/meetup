package com.example.meetup.payments.infrastructure.aggregateStore;

import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.example.meetup.payments.base.application.IProjector;
import com.example.meetup.payments.base.domain.IDomainEvent;
import com.example.meetup.payments.base.infrastructure.ISubscriptionsManager;


@Component
public class PaymentsSubscriptionsManager implements ISubscriptionsManager {

	private final List<IProjector> projectors;
	
	public PaymentsSubscriptionsManager(List<IProjector> projectors) {
		this.projectors = projectors;
	}
	
	@Override
	public void addEvent(IDomainEvent event) {
	
		projectors.forEach(projector -> projector.project(event));
	}

}
