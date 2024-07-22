package com.example.meetup.payments.base.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;


public abstract class Aggregate {

	@Getter
	private List<IDomainEvent> domainEvents;

	public abstract String getStreamId();

	@Getter
	@Setter(value = AccessLevel.PRIVATE)
	private int version;

	protected Aggregate() {
		domainEvents = new ArrayList<IDomainEvent>();
		version = -1;
	}
	
	// public for testing
	public void addDomainEvent(IDomainEvent event) {
		domainEvents.add(event);
	}

	public void load(Iterable<IDomainEvent> history) {
		for (var e : history) {
			if (!apply(e)) {
				applyOnChildEnitites(e, this);
			}
			version++;
		}
	}

	protected abstract boolean apply(IDomainEvent event);
	
	private boolean applyOnChildEnitites(IDomainEvent event, Object self) {
	
		List<Entity> childEntities;
		
		if (self instanceof Aggregate aggregate)
			childEntities = aggregate.getDirectChildEntities();
		else
			childEntities = ((Entity) self).getDirectChildEntities();
			
		for (var entity : childEntities) {			
			if (entity.apply(event))
				return true;			
			if (applyOnChildEnitites(event, entity))
				return true;			
		}
		
		return false;
	}
	
	protected static void checkRule(IBusinessRule rule) {
		if (rule.isBroken()) {
			throw new BusinessRuleValidationException(rule);
		}
	}

	public abstract List<Entity> getDirectChildEntities();
	
	// for testing
	public void clearDomainEvents() {
		domainEvents.clear();
	}

}
