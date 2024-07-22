package com.example.meetup.userAccess.application.registerNewUser;


import java.util.UUID;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.stereotype.Component;

import com.example.meetup.userAccess.application.confirmationLinks.view.GetConfirmationLinksByLoginQuery;
import com.example.meetup.userAccess.base.application.ISyncCommandHandler;
import com.example.meetup.userAccess.base.domain.IAggregateStore;
import com.example.meetup.userAccess.base.infrastructure.IQueryDispatcher;
import com.example.meetup.userAccess.domain.userRegistrations.UserRegistration;

import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class RegisterNewUserCommandHandler implements ISyncCommandHandler<RegisterNewUserCommand, String> {
	
	private final IAggregateStore aggregateStore;
	private final IQueryDispatcher queryDispatcher;

	private final OAuth2AuthorizedClientManager clientManager;
	private final CheckLoginClient checkLoginClient;
	
	private final RuntimeService runtimeService;
	
	@Override
	public String handle(RegisterNewUserCommand command) {	

		OAuth2AuthorizeRequest request = OAuth2AuthorizeRequest.
				withClientRegistrationId("1").
				principal("client")
				.build();
		var client = clientManager.authorize(request);
		var token = client.getAccessToken().getTokenValue();
		

		if (checkLoginClient.check("Bearer " + token, new CheckLoginClient.CheckUser(command.getLogin()))
				&& null == queryDispatcher.execute(new GetConfirmationLinksByLoginQuery(command.getLogin()))) {
			
			var userRegistration = new UserRegistration(
					command.getLogin(), 
					command.getFirstName(), 
					command.getLastName(), 
					command.getEmail(), 
					command.getPassword()); 
			
			aggregateStore.save(userRegistration);
						
			BusinessKeyHolder.businessKey = UUID.randomUUID().toString();
			ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("userRegistration", BusinessKeyHolder.businessKey);
			
//			BusinessKeyHolder.businessKey = processInstance.getBusinessKey();
			
			return userRegistration.getConfirmLink();
		}
		return "login name already in use";
	}	
    public static class BusinessKeyHolder {
    	public static String businessKey;
    }
}

