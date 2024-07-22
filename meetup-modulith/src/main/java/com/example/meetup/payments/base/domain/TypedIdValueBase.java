package com.example.meetup.payments.base.domain;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
public class TypedIdValueBase<V> implements Serializable {

	@Getter
	private V value;
	
	public TypedIdValueBase(V value) {
		if (value == null)
			throw new IllegalArgumentException("Id value cannot be null!");
		this.value = value;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof TypedIdValueBase))
			return false;
		return value.equals(((TypedIdValueBase) obj).getValue());
	}
	
	@Override
	public int hashCode() {
		return value.hashCode();
	}
	
	public boolean notEquals(Object obj) {
		return !equals(obj);
	}
	
}
