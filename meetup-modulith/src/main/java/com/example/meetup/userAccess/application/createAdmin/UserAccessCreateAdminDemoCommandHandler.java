package com.example.meetup.userAccess.application.createAdmin;

import java.util.UUID;

import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.stereotype.Component;

import com.example.meetup.userAccess.application.addUserToAuthServer.RegisterClient;
import com.example.meetup.userAccess.application.registerNewUser.CheckLoginClient;
import com.example.meetup.userAccess.base.SystemClock;
import com.example.meetup.userAccess.base.application.ISyncCommandHandler;
import com.example.meetup.userAccess.base.domain.IAggregateStore;
import com.example.meetup.userAccess.base.infrastructure.IQueryDispatcher;
import com.example.meetup.userAccess.domain.users.User;
import com.example.meetup.userAccess.domain.users.UserType;

import lombok.RequiredArgsConstructor;
    
@Component
@RequiredArgsConstructor
public class UserAccessCreateAdminDemoCommandHandler implements ISyncCommandHandler<UserAccessCreateAdminDemoCommand, UUID> {

	private final IAggregateStore aggregateStore;
	private final IQueryDispatcher queryDispatcher;
	private final OAuth2AuthorizedClientManager clientManager;
	private final CheckLoginClient checkLoginClient;
	private final RegisterClient registerClient;

	@Override
	public UUID handle(UserAccessCreateAdminDemoCommand command) {

		var admin = new User(
				command.getLogin(),
				command.getFirstName(),
				command.getLastName(),
				command.getEmail(),
				SystemClock.now(),
				UserType.admin);

		aggregateStore.save(admin);
		
		return admin.getUserId().getValue();
	}	
    	
}
