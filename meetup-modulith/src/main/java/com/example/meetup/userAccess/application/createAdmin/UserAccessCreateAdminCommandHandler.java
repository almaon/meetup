package com.example.meetup.userAccess.application.createAdmin;

import java.util.List;

import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.stereotype.Component;

import com.example.meetup.infrastructure.auth.PasswordEncoderAccessor;
import com.example.meetup.userAccess.application.addUserToAuthServer.RegisterClient;
import com.example.meetup.userAccess.application.confirmationLinks.view.GetConfirmationLinksByLoginQuery;
import com.example.meetup.userAccess.application.registerNewUser.CheckLoginClient;
import com.example.meetup.userAccess.base.SystemClock;
import com.example.meetup.userAccess.base.application.IAsyncCommandHandler;
import com.example.meetup.userAccess.base.domain.IAggregateStore;
import com.example.meetup.userAccess.base.infrastructure.IQueryDispatcher;
import com.example.meetup.userAccess.domain.users.User;
import com.example.meetup.userAccess.domain.users.UserType;

import lombok.RequiredArgsConstructor;
    
@Component
@RequiredArgsConstructor
public class UserAccessCreateAdminCommandHandler implements IAsyncCommandHandler<UserAccessCreateAdminCommand> {

	private final IAggregateStore aggregateStore;
	private final IQueryDispatcher queryDispatcher;
	private final OAuth2AuthorizedClientManager clientManager;
	private final CheckLoginClient checkLoginClient;
	private final RegisterClient registerClient;

	@Override
	public void handle(UserAccessCreateAdminCommand command) {

		OAuth2AuthorizeRequest request = OAuth2AuthorizeRequest.
				withClientRegistrationId("1").
				principal("client")
				.build();
		var client = clientManager.authorize(request);
		var token = client.getAccessToken().getTokenValue();
		

		if (checkLoginClient.check("Bearer " + token, new CheckLoginClient.CheckUser(command.getLogin()))
				&& null == queryDispatcher.execute(new GetConfirmationLinksByLoginQuery(command.getLogin()))) {
			
			var admin = new User(
					command.getLogin(),
					command.getFirstName(),
					command.getLastName(),
					command.getEmail(),
					SystemClock.now(),
					UserType.admin);
			
			aggregateStore.save(admin);
			
			registerClient.register("Bearer " + token, 
					new RegisterClient.RegisterUserRequest(
							command.getLogin(), 
							PasswordEncoderAccessor.encoder.encode(command.getPassword()),
							List.of(UserType.admin.name())));
		}
	}	
    	
}
