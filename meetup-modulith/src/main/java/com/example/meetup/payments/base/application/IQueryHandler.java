package com.example.meetup.payments.base.application;


public interface IQueryHandler<IN extends IQuery, OUT> {
    OUT handle(IN query);
}
