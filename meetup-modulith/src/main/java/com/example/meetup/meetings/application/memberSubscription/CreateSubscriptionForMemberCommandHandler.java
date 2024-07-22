package com.example.meetup.meetings.application.memberSubscription;


import org.springframework.stereotype.Component;

import com.example.meetup.meetings.base.application.IAsyncCommandHandler;
import com.example.meetup.meetings.base.domain.IAggregateStore;
import com.example.meetup.meetings.domain.member.MemberId;
import com.example.meetup.meetings.domain.memberSubscriptions.MemberSubscription;

import lombok.RequiredArgsConstructor;

    
@Component
@RequiredArgsConstructor
public class CreateSubscriptionForMemberCommandHandler implements IAsyncCommandHandler<CreateSubscriptionForMemberCommand> {

	private final IAggregateStore aggregateStore;


	@Override
	public void handle(CreateSubscriptionForMemberCommand command) {

   		var memberSubscription = new MemberSubscription(
   			new MemberId(command.getMemberId()),
   			command.getExpirationDate());
    	
		aggregateStore.save(memberSubscription);

	}	
    	
}
