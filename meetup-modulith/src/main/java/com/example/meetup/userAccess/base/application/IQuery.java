package com.example.meetup.userAccess.base.application;

public interface IQuery {

    Class<? extends IQueryHandler> getHandlerType();
}
