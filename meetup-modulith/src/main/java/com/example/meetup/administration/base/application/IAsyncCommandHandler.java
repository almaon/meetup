package com.example.meetup.administration.base.application;


public interface IAsyncCommandHandler<IN extends ICommand> extends ICommandHandler {

    void handle(IN command);
}
