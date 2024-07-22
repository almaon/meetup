package com.example.meetup.userAccess.base.application;


public interface IAsyncCommandHandler<IN extends ICommand> extends ICommandHandler {

    void handle(IN command);
}
