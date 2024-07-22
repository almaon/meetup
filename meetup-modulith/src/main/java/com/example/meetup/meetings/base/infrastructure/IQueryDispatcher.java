package com.example.meetup.meetings.base.infrastructure;

import com.example.meetup.meetings.base.application.IQuery;

public interface IQueryDispatcher {

	<T> T executeQuery(IQuery query);
    
}
