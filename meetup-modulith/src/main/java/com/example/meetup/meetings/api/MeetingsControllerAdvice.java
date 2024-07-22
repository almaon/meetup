package com.example.meetup.meetings.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.meetup.meetings.base.domain.BusinessRuleValidationException;

@RestControllerAdvice
public class MeetingsControllerAdvice {

    
    @ExceptionHandler(BusinessRuleValidationException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<ErrorMessage> brokenRuleHandler(BusinessRuleValidationException ex) {
//    	return ex.getDetails();
    	
    	   ErrorMessage message = new ErrorMessage(
    			   ex.getDetails());
    		    
    	   return new ResponseEntity<ErrorMessage>(message, HttpStatus.NOT_FOUND);
    }  

}

record ErrorMessage(String message) {
}
