package com.example.meetup.userAccess.application.sendRegistrationConfirmationLink;

import org.springframework.stereotype.Component;

import com.example.meetup.userAccess.base.application.IAsyncCommandHandler;

import lombok.RequiredArgsConstructor;
    
@Component
@RequiredArgsConstructor
public class SendRegistrationConfirmationLinkCommandHandler implements IAsyncCommandHandler<SendRegistrationConfirmationLinkCommand> {

	@Override
	public void handle(SendRegistrationConfirmationLinkCommand command) {
	 	// TODO: send email
	}	
    	
}
