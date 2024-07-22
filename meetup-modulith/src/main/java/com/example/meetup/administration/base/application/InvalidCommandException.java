package com.example.meetup.administration.base.application;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class InvalidCommandException extends RuntimeException {

	@Getter
	private final List<String> errors;
	
}
