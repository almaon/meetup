package com.example.meetup.payments.domain.payers;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.meetup.payments.base.domain.Aggregate;
import com.example.meetup.payments.base.domain.Entity;
import com.example.meetup.payments.base.domain.IDomainEvent;
import com.example.meetup.payments.domain.payers.events.PayerCreatedEvent;

import lombok.Getter;
import lombok.Setter;


@Getter
public class Payer extends Aggregate {
	
	// business id
	@Setter // for testing
	protected PayerId payerId;
	
	public String getStreamId() {
		return "Payments-Payer-" + payerId.getValue();
	}
		
	protected String login;
	protected String email;
	protected String firstName;
	protected String lastName;
	protected LocalDateTime registerDate;
	
	public Payer() {
	}
	
	public Payer(PayerId payerId, String login, String email, String firstName, String lastName, LocalDateTime registerDate) {

		var payerCreated = new PayerCreatedEvent(
			payerId.getValue(),
			lastName,
			email,
			firstName,
			login,
			registerDate);
 
		apply(payerCreated);
		addDomainEvent(payerCreated);

	}
	
	
	
	
	private boolean when(PayerCreatedEvent event) {

		payerId = new PayerId(event.getPayerId());
		lastName = event.getLastName();
		email = event.getEmail();
		firstName = event.getFirstName();
		login = event.getLogin();
		registerDate = event.getRegisterDate();
		return true;

	}
	
	@Override
	protected boolean apply(IDomainEvent event) {
	
		if (event instanceof PayerCreatedEvent castedEvent) {
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
