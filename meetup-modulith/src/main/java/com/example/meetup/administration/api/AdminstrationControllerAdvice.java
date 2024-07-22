package com.example.meetup.administration.api;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.meetup.administration.base.application.InvalidCommandException;
import com.example.meetup.administration.infrastructure.exception.NotFoundException;

@RestControllerAdvice
public class AdminstrationControllerAdvice {

    
    @ExceptionHandler(InvalidCommandException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    String brokenRuleHandler(InvalidCommandException ex) {
    	return ex.getErrors().stream().collect(Collectors.joining(",\n"));
    }  
    
  
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String brokenRuleHandler(NotFoundException ex) {
    	return ex.getMessage();
    }  
}
