package com.example.meetup.meetings.base.application;


public interface ISyncCommandHandler<IN extends ICommand, OUT> extends ICommandHandler  {

    OUT handle(IN command);
}
