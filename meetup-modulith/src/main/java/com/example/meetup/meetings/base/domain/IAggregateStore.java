package com.example.meetup.meetings.base.domain;
	
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
	
public interface IAggregateStore {

	void save(Aggregate... aggregates);

	<T extends Aggregate, V> T load(TypedIdValueBase<V> aggregateId, Class<? extends Aggregate> aggregateType);

	List<IDomainEvent> getDomainEvents();

	void clearDomainEvents();
}
