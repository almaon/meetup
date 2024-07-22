package com.example.meetup.meetings.domain.memberSubscriptions;

import com.example.meetup.meetings.base.domain.TypedIdValueBase;

import java.util.UUID;


public class MemberSubscriptionId extends TypedIdValueBase<UUID> {

	public MemberSubscriptionId(UUID value) {
		super(value);
	}

}
