package com.example.meetup.userAccess.base.domain;


public interface IBusinessRule {

	boolean isBroken();
	
	String message();
}
