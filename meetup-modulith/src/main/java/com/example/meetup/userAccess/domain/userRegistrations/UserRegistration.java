package com.example.meetup.userAccess.domain.userRegistrations;


import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.example.meetup.infrastructure.auth.PasswordEncoderAccessor;
import com.example.meetup.userAccess.base.SystemClock;
import com.example.meetup.userAccess.base.domain.Aggregate;
import com.example.meetup.userAccess.base.domain.Entity;
import com.example.meetup.userAccess.base.domain.IDomainEvent;
import com.example.meetup.userAccess.domain.userRegistrations.events.NewUserRegisteredEvent;
import com.example.meetup.userAccess.domain.userRegistrations.events.UserRegistrationConfirmedEvent;

import lombok.Getter;
import lombok.Setter;


@Getter
public class UserRegistration extends Aggregate {
	
	// business id
	@Setter // for testing
	protected UserRegistrationId userRegistrationId;
	
	public String getStreamId() {
		return "UserAccess-UserRegistration-" + userRegistrationId.getValue();
	}
		
	protected String lastName;
	protected String login;
	protected LocalDateTime registerDate;
	protected String email;
	protected String firstName;
	protected UserRegistrationStatus status;
	protected LocalDateTime confirmedDate;
	protected String encodedPassword;
	protected String confirmLink;
	
	public UserRegistration() {
	}
	
	public UserRegistration(String login, String firstName, String lastName, String email, String password) {

		byte[] array = new byte[20];
		new Random().nextBytes(array); 
	    String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	    SecureRandom RANDOM = new SecureRandom();
        StringBuilder sb = new StringBuilder(20);
        for (int i = 0; i < 16; i++) {
            int index = RANDOM.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }
        var confirmLink = sb.toString();
	    
		var newUserRegistered = new NewUserRegisteredEvent(
			lastName,
			email,
			UUID.randomUUID(),
			login,
			firstName,
			confirmLink,
			SystemClock.now(),
			PasswordEncoderAccessor.encoder.encode(password),
			UserRegistrationStatus.WaitingForConfirmationx);
 
		apply(newUserRegistered);
		addDomainEvent(newUserRegistered);

	}
	
	public void confirmRegistration() {

		var userRegistrationConfirmed = new UserRegistrationConfirmedEvent(
			userRegistrationId.getValue(),
			registerDate,
			login,
			lastName,
			firstName,
			email,
			encodedPassword);
 
		apply(userRegistrationConfirmed);
		addDomainEvent(userRegistrationConfirmed);

	}	
	
	
	
	private boolean when(NewUserRegisteredEvent event) {

		userRegistrationId = new UserRegistrationId(event.getUserRegistrationId());
		lastName = event.getLastName();
		email = event.getEmail();
		login = event.getLogin();
		firstName = event.getFirstName();
		registerDate = event.getRegisterDate();
		encodedPassword = event.getEncodedPassword();
		status = event.getStatus();
		confirmLink = event.getConfirmLink();
		return true;

	}
	
	private boolean when(UserRegistrationConfirmedEvent event) {

		status = UserRegistrationStatus.Confirmed;
		return true;

	}
	
	@Override
	protected boolean apply(IDomainEvent event) {
	
		if (event instanceof NewUserRegisteredEvent castedEvent) {
			return when(castedEvent);
		}
	
		if (event instanceof UserRegistrationConfirmedEvent castedEvent) {
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
