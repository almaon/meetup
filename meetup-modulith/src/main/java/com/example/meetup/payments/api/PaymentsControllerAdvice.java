package com.example.meetup.payments.api;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.meetup.payments.base.application.InvalidCommandException;

@RestControllerAdvice
public class PaymentsControllerAdvice {

    
    @ExceptionHandler(InvalidCommandException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    String brokenRuleHandler(InvalidCommandException ex) {
    	return ex.getErrors().stream().collect(Collectors.joining(",\n"));
    }  

}
