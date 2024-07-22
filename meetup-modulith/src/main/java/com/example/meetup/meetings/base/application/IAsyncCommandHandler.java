package com.example.meetup.meetings.base.application;


public interface IAsyncCommandHandler<IN extends ICommand> extends ICommandHandler {

    void handle(IN command);
}
