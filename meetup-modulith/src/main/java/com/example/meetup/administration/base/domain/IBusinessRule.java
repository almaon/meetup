package com.example.meetup.administration.base.domain;


public interface IBusinessRule {

	boolean isBroken();
	
	String message();
}
