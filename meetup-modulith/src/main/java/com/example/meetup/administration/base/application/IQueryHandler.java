package com.example.meetup.administration.base.application;


public interface IQueryHandler<IN extends IQuery, OUT> {
    OUT handle(IN query);
}
