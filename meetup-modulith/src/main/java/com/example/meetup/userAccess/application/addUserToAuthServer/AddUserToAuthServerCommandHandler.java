package com.example.meetup.userAccess.application.addUserToAuthServer;


import java.util.List;

import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.stereotype.Component;

import com.example.meetup.userAccess.base.application.IAsyncCommandHandler;
import com.example.meetup.userAccess.domain.users.UserType;

import lombok.RequiredArgsConstructor;

    
@Component
@RequiredArgsConstructor
public class AddUserToAuthServerCommandHandler implements IAsyncCommandHandler<AddUserToAuthServerCommand> {

	private final OAuth2AuthorizedClientManager clientManager;
	private final RegisterClient registerClient;

	@Override
	public void handle(AddUserToAuthServerCommand command) {

		OAuth2AuthorizeRequest request = OAuth2AuthorizeRequest.
				withClientRegistrationId("1").
				principal("client")
				.build();
		var client = clientManager.authorize(request);
		var token = client.getAccessToken().getTokenValue();
		

		registerClient.register("Bearer " + token, 
				new RegisterClient.RegisterUserRequest(
						command.getLogin(), 
						command.getEncodedPassword(),
						List.of(UserType.user.name())));
	}	
    	
}