package com.example.meetup.userAccess.base.domain;

import java.util.List;
import java.util.UUID;

import lombok.AccessLevel;
import lombok.Setter;
import lombok.Getter;

public abstract class Entity {

	@Getter
	private List<IDomainEvent> domainEvents;

	@Getter
	@Setter(value = AccessLevel.PROTECTED)
	private UUID id;

	@Getter
	@Setter(value = AccessLevel.PRIVATE)
	private int version;
	
	protected Aggregate aggregateRoot;

	protected Entity(Aggregate root) {
		aggregateRoot = root;
	}
	
	protected void addDomainEvent(IDomainEvent event) {
		aggregateRoot.getDomainEvents().add(event);
	}

	protected abstract boolean apply(IDomainEvent event);
	
	protected static void checkRule(IBusinessRule rule) {
		if (rule.isBroken()) {
			throw new BusinessRuleValidationException(rule);
		}
	}
	
	public abstract List<Entity> getDirectChildEntities();

}
