package com.example.meetup.administration.base.infrastructure;

import com.example.meetup.administration.base.application.IQuery;

public interface IQueryDispatcher {

	<T> T executeQuery(IQuery query);
    
}
