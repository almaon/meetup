package com.example.meetup.payments.base.domain;


public interface IBusinessRule {

	boolean isBroken();
	
	String message();
}
