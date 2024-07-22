package com.example.meetup.userAccess.base.domain;
	
import java.util.List;
	
public interface IAggregateStore {

	void save(Aggregate... aggregates);

	<T extends Aggregate, V> T load(TypedIdValueBase<V> aggregateId, Class<? extends Aggregate> aggregateType);

	List<IDomainEvent> getDomainEvents();

	void clearDomainEvents();
}
