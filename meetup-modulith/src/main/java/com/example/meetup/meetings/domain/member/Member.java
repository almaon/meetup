package com.example.meetup.meetings.domain.member;


import com.example.meetup.meetings.base.domain.Aggregate;
import com.example.meetup.meetings.base.domain.IDomainEvent;
import com.example.meetup.meetings.base.domain.Entity;

	
import com.example.meetup.meetings.domain.member.events.MemberCreatedEvent;

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
		return "Meetings-Member-" + memberId.getValue();
	}
		
	protected String firstName;
	protected String login;
	protected String email;
	protected String lastName;
	protected LocalDateTime registerDate;
	
	public Member() {
	}
	
	public Member(MemberId memberId, String login, String email, String firstName, String lastName, LocalDateTime registerDate) {

		var memberCreated = new MemberCreatedEvent(
			memberId.getValue(),
			email,
			login,
			firstName,
			lastName,
			registerDate);
 
		apply(memberCreated);
		addDomainEvent(memberCreated);

	}
	
	
	
	
	private boolean when(MemberCreatedEvent event) {

		memberId = new MemberId(event.getMemberId());
		email = event.getEmail();
		login = event.getLogin();
		firstName = event.getFirstName();
		lastName = event.getLastName();
		registerDate = event.getRegisterDate();
		return true;

	}
	
	@Override
	protected boolean apply(IDomainEvent event) {
	
		var processed = false;

		if (event instanceof MemberCreatedEvent castedEvent) {
			processed =  when(castedEvent);
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
