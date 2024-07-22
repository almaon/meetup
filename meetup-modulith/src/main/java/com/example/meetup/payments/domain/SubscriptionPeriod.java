package com.example.meetup.payments.domain;


import com.example.meetup.payments.base.domain.ValueObject;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;


@EqualsAndHashCode(callSuper = false)
@Getter
@AllArgsConstructor
public class SubscriptionPeriod extends ValueObject {

	private SubscriptionPeriodCode code;
	
	public SubscriptionPeriod(String code) {

		this.code = SubscriptionPeriodCode.valueOf(code);

	}
	
	public String getName() {

		 return code.name();

	}	
	public int getMonthsNumber() {

		return code == SubscriptionPeriodCode.Month ? 1 : 6;

	}	

}
