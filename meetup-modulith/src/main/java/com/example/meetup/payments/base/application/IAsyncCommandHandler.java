package com.example.meetup.payments.base.application;


public interface IAsyncCommandHandler<IN extends ICommand> extends ICommandHandler {

    void handle(IN command);
}
