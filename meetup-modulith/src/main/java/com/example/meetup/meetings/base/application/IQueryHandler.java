package com.example.meetup.meetings.base.application;


public interface IQueryHandler<IN extends IQuery, OUT> {
    OUT handle(IN query);
}
