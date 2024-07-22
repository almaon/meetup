package com.example.meetup.payments.base.infrastructure;

import com.example.meetup.payments.base.application.IQuery;

public interface IQueryDispatcher {

	<T> T executeQuery(IQuery query);
    
}
