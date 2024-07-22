package com.example.meetup.userAccess.base.application;


public interface IQueryHandler<IN extends IQuery, OUT> {
    OUT handle(IN query);
}
