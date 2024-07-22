package com.example.meetup.payments.base.application;

public interface IQuery {

    Class<? extends IQueryHandler> getHandlerType();
}
