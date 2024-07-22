package com.example.meetup.payments.base.domain;

import lombok.Getter;

public abstract class AggregateId<T extends Aggregate, V> {

	@Getter
	private V value;
	
	@Getter
	protected Class<? extends Aggregate> aggregateType;
	
	protected AggregateId(V value) {
		this.value = value;
	}
	
}
