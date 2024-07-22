package com.example.meetup.userAccess.application.administrationCreateUser;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.example.meetup.userAccess.base.application.ISyncCommandHandler;
import com.example.meetup.userAccess.base.domain.IAggregateStore;
import com.example.meetup.userAccess.domain.users.User;
import com.example.meetup.userAccess.domain.users.UserType;

import lombok.RequiredArgsConstructor;
    
@Component
@RequiredArgsConstructor
public class AdministrationCreateUserCommandHandler implements ISyncCommandHandler<AdministrationCreateUserCommand, UUID> {

	private final IAggregateStore aggregateStore;

	@Override
	public UUID handle(AdministrationCreateUserCommand command) {

		var user = new User(
   			command.getLogin(), 
   			command.getFirstName(), 
   			command.getLastName(), 
   			command.getEmail(), 
   			command.getRegisterDate(),
   			UserType.user);
		
		aggregateStore.save(user);
		
		return user.getUserId().getValue();
	}	
    	
}
