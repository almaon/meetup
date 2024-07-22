package com.example.meetup.userAccess.base.application;

import java.util.UUID;


public interface ICommand {

    UUID getId();

    Class<? extends ICommandHandler> getHandlerType();
}
