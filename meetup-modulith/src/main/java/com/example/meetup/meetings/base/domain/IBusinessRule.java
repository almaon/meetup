package com.example.meetup.meetings.base.domain;


public interface IBusinessRule {

	boolean isBroken();
	
	String message();
}
