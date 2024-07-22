package com.example.meetup.administration.base.application;

public interface IQuery {

    Class<? extends IQueryHandler> getHandlerType();
}
