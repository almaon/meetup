package com.example.meetup.administration.base.domain;
	
import java.util.List;
import java.util.Optional;
	
public interface IAggregateStore {

	void save(Aggregate... aggregates);

	<T extends Aggregate, V> Optional<T> load(TypedIdValueBase<V> aggregateId, Class<? extends Aggregate> aggregateType);

	List<IDomainEvent> getDomainEvents();

	void clearDomainEvents();
}
