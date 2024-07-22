package com.example.meetup.meetings.domain;

import com.example.meetup.meetings.base.domain.ValueObject;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = false)
@Getter
@AllArgsConstructor
public class MoneyValue extends ValueObject {

	private double value;
	private String currency;
	
	public static MoneyValue undefined() {
		return new MoneyValue(0.0, null);
	}

}
