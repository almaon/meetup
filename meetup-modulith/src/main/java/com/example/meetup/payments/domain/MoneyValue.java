package com.example.meetup.payments.domain;


import com.example.meetup.payments.base.domain.ValueObject;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;


@EqualsAndHashCode(callSuper = false)
@Getter
@AllArgsConstructor
public class MoneyValue extends ValueObject {

	private double value;
	private String currency;
	
	

}
