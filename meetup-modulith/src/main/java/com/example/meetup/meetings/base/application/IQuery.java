package com.example.meetup.meetings.base.application;

public interface IQuery {

    Class<? extends IQueryHandler> getHandlerType();
}
