package com.example.meetup.userAccess.base.domain;

import lombok.Getter;
import lombok.ToString;


@Getter
@ToString
public class BusinessRuleValidationException extends RuntimeException {

	private IBusinessRule brokenRule;

	private String details;

	public BusinessRuleValidationException(IBusinessRule brokenRule) {
		this.brokenRule = brokenRule;
		details = brokenRule.message();
	}
	
}
