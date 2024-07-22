package com.example.meetup.meetings.domain.memberSubscriptions;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.meetup.meetings.base.domain.Aggregate;
import com.example.meetup.meetings.base.domain.Entity;
import com.example.meetup.meetings.base.domain.IDomainEvent;
import com.example.meetup.meetings.domain.member.MemberId;
import com.example.meetup.meetings.domain.memberSubscriptions.events.MemberSubscriptionCreatedEvent;
import com.example.meetup.meetings.domain.memberSubscriptions.events.MemberSubscriptionExpirationDateChangedEvent;

import lombok.Getter;
import lombok.Setter;


@Getter
public class MemberSubscription extends Aggregate {
	
	// business id
	@Setter // for testing
	protected MemberSubscriptionId aggregateId;
	
	public String getStreamId() {
		return "Meetings-MemberSubscription-" + aggregateId.getValue();
	}
		
	protected LocalDateTime expirationDate;
	
	public MemberSubscription() {
	}
	
	public MemberSubscription(MemberId memberId, LocalDateTime expirationDate) {

		var memberSubscriptionCreated = new MemberSubscriptionCreatedEvent(
			memberId.getValue(),
			expirationDate);
 
		apply(memberSubscriptionCreated);
		addDomainEvent(memberSubscriptionCreated);

	}
	
	public void changeExpirationDate(LocalDateTime expirationDate) {

		var memberSubscriptionExpirationDateChanged = new MemberSubscriptionExpirationDateChangedEvent(
			aggregateId.getValue(),
			expirationDate);
 
		apply(memberSubscriptionExpirationDateChanged);
		addDomainEvent(memberSubscriptionExpirationDateChanged);

	}	
	
	
	
	private boolean when(MemberSubscriptionCreatedEvent event) {

		aggregateId = new MemberSubscriptionId(event.getMemberId());
		expirationDate = event.getExpirationDate();
		return true;

	}
	
	private boolean when(MemberSubscriptionExpirationDateChangedEvent event) {

		expirationDate = event.getExpirationDate();
		
		return true;

	}
	
	@Override
	protected boolean apply(IDomainEvent event) {
	
		var processed = false;

		if (event instanceof MemberSubscriptionCreatedEvent castedEvent) {
			processed = when(castedEvent);
		}
		else if (event instanceof MemberSubscriptionExpirationDateChangedEvent castedEvent) {
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
