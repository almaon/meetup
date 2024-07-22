package com.example.meetup.userAccess.base.infrastructure;

import com.example.meetup.userAccess.base.domain.IDomainEvent;

public interface ISubscriptionsManager {

	void addEvent(IDomainEvent event);
}
