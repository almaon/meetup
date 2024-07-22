package com.example.meetup.userAccess.base.domain;


public abstract class ValueObject {

	protected static void checkRule(IBusinessRule rule) {
		if (rule.isBroken())
			throw new BusinessRuleValidationException(rule);
	}
	
	public boolean notEquals(Object obj) {
		return !equals(obj);
	}

}