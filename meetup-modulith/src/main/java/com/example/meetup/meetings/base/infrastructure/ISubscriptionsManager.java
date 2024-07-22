package com.example.meetup.meetings.base.infrastructure;

import com.example.meetup.meetings.base.domain.IDomainEvent;

public interface ISubscriptionsManager {

	void addEvent(IDomainEvent event);
}
