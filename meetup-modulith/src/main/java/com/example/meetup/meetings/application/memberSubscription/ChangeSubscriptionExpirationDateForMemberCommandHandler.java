package com.example.meetup.meetings.application.memberSubscription;


import org.springframework.stereotype.Component;

import com.example.meetup.meetings.base.application.IAsyncCommandHandler;
import com.example.meetup.meetings.base.domain.IAggregateStore;
import com.example.meetup.meetings.domain.memberSubscriptions.MemberSubscription;
import com.example.meetup.meetings.domain.memberSubscriptions.MemberSubscriptionId;

import lombok.RequiredArgsConstructor;

    
@Component
@RequiredArgsConstructor
public class ChangeSubscriptionExpirationDateForMemberCommandHandler implements IAsyncCommandHandler<ChangeSubscriptionExpirationDateForMemberCommand> {

	private final IAggregateStore aggregateStore;


	@Override
	public void handle(ChangeSubscriptionExpirationDateForMemberCommand command) {

		MemberSubscription memberSubscriptions = aggregateStore.load(new MemberSubscriptionId(command.getMemberId()), MemberSubscription.class);
    	
		memberSubscriptions.changeExpirationDate(command.getExpirationDate());
		
		aggregateStore.save(memberSubscriptions);

	}	
    	
}
