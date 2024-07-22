package com.example.meetup.payments.base.infrastructure;

import com.example.meetup.payments.base.domain.IDomainEvent;

public interface ISubscriptionsManager {

	void addEvent(IDomainEvent event);
}
