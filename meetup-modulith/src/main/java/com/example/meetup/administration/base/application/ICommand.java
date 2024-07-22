package com.example.meetup.administration.base.application;

import java.util.UUID;


public interface ICommand {

    UUID getId();

    Class<? extends ICommandHandler> getHandlerType();
}
