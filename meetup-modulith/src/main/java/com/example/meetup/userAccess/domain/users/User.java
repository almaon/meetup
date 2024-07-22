package com.example.meetup.userAccess.domain.users;


import com.example.meetup.userAccess.base.domain.Aggregate;
import com.example.meetup.userAccess.base.domain.IDomainEvent;
import com.example.meetup.userAccess.base.domain.Entity;

	
import com.example.meetup.userAccess.domain.users.events.UserCreatedEvent;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;


@Getter
public class User extends Aggregate {
	
	// business id
	@Setter // for testing
	protected UserId userId;
	
	public String getStreamId() {
		return "UserAccess-User-" + userId.getValue();
	}
		
	protected String lastName;
	protected String firstName;
	protected LocalDateTime registerDate;
	protected String login;
	protected String email;
	protected UserType userType;
	
	public User() {
	}
	
	public User(String login, String firstName, String lastName, String email, LocalDateTime registrationDate, UserType userType) {

		var userCreated = new UserCreatedEvent(
			UUID.randomUUID(),
			firstName,
			registrationDate,
			lastName,
			login,
			email,
			userType);
 
		apply(userCreated);
		addDomainEvent(userCreated);

	}
	
	
	
	
	private boolean when(UserCreatedEvent event) {

		userId = new UserId(event.getUserId());
		firstName = event.getFirstName();
		registerDate = event.getRegisterDate();
		lastName = event.getLastName();
		login = event.getLogin();
		email = event.getEmail();
		userType = event.getUserType();
		return true;

	}
	
	@Override
	protected boolean apply(IDomainEvent event) {
	
		if (event instanceof UserCreatedEvent castedEvent) {
			return when(castedEvent);
		}
		return false;
	}
	
	@Override
	public List<Entity> getDirectChildEntities() {
		var entities = new ArrayList<Entity>();
		return entities;
	}
	
	
}
