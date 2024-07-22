package com.example.meetup.userAccess.base.infrastructure;

import com.example.meetup.userAccess.base.application.IQuery;

public interface IQueryDispatcher {

	<T> T execute(IQuery query);
    
}
