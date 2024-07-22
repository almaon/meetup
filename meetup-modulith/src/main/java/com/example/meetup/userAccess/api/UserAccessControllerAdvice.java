package com.example.meetup.userAccess.api;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.meetup.userAccess.base.application.InvalidCommandException;

@RestControllerAdvice
public class UserAccessControllerAdvice {
    
    @ExceptionHandler(InvalidCommandException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String brokenRuleHandler(InvalidCommandException ex) {
    	return ex.getErrors().stream().collect(Collectors.joining(",\n"));
    }  

}
