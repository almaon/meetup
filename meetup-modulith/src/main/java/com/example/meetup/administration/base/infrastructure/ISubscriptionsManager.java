package com.example.meetup.administration.base.infrastructure;

import com.example.meetup.administration.base.domain.IDomainEvent;

public interface ISubscriptionsManager {

	void addEvent(IDomainEvent event);
}
