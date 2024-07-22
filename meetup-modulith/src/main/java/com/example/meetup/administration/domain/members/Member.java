package com.example.meetup.administration.domain.members;


import com.example.meetup.administration.base.domain.Aggregate;
import com.example.meetup.administration.base.domain.IDomainEvent;
import com.example.meetup.administration.base.domain.Entity;

	
import com.example.meetup.administration.domain.members.events.MemberCreatedEvent;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;


@Getter
public class Member extends Aggregate {
	
	// business id
	@Setter // for testing
	protected MemberId memberId;
	
	public String getStreamId() {
		return "Administration-Member-" + memberId.getValue();
	}
		
	protected String login;
	protected String email;
	protected String firstName;
	protected String lastName;
	protected LocalDateTime registerDate;
	
	public Member() {
	}
	
	public Member(MemberId memberId, String login, String email, String firstName, String lastName, LocalDateTime registerDate) {

		var memberCreated = new MemberCreatedEvent(
			memberId.getValue(),
			firstName,
			lastName,
			email,
			login,
			registerDate);
 
		apply(memberCreated);
		addDomainEvent(memberCreated);

	}
	
	
	
	
	private boolean when(MemberCreatedEvent event) {

		memberId = new MemberId(event.getMemberId());
		firstName = event.getFirstName();
		lastName = event.getLastName();
		email = event.getEmail();
		login = event.getLogin();
		registerDate = event.getRegisterDate();
		return true;

	}
	
	@Override
	protected boolean apply(IDomainEvent event) {
	
		var processed = false;
		
		if (event instanceof MemberCreatedEvent castedEvent) {
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
