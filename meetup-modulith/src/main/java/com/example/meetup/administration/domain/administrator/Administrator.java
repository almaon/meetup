package com.example.meetup.administration.domain.administrator;


import com.example.meetup.administration.base.domain.Aggregate;
import com.example.meetup.administration.base.domain.IDomainEvent;
import com.example.meetup.administration.base.domain.Entity;

	
import com.example.meetup.administration.domain.administrator.events.AdminCreatedEvent;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;


@Getter
public class Administrator extends Aggregate {
	
	// business id
	@Setter // for testing
	protected AdministratorId administratorId;
	
	public String getStreamId() {
		return "Administration-Administrator-" + administratorId.getValue();
	}
		
	protected String firstName;
	protected String login;
	protected String email;
	protected String lastName;
	protected LocalDateTime registerDate;
	
	public Administrator() {
	}
	
	public Administrator(AdministratorId adminId, String login, String email, String firstName, String lastName, LocalDateTime registerDate) {

		var adminCreated = new AdminCreatedEvent(
			lastName,
			firstName,
			email,
			login,
			adminId.getValue(),
			registerDate);
 
		apply(adminCreated);
		addDomainEvent(adminCreated);

	}
	
	
	
	
	private boolean when(AdminCreatedEvent event) {

		administratorId = new AdministratorId(event.getAdminId());
		lastName = event.getLastName();
		firstName = event.getFirstName();
		email = event.getEmail();
		login = event.getLogin();
		registerDate = event.getRegisterDate();
		return true;

	}
	
	@Override
	protected boolean apply(IDomainEvent event) {
	
		var processed = false;
		
		if (event instanceof AdminCreatedEvent castedEvent) {
			processed = when(castedEvent);
		}
		
		if (processed)
			incrementVersion();
		
		return processed;
	}
	
	@Override
	public List<Entity> getDirectChildEntities() {
		var entities = new ArrayList<Entity>();
		return entities;
	}
	
	
}
