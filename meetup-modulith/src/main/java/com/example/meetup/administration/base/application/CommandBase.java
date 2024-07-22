package com.example.meetup.administration.base.application;

import lombok.Getter;

import java.util.UUID;


@Getter
public abstract class CommandBase implements ICommand {

    private final UUID id;

    protected CommandBase() {
        id = UUID.randomUUID();
    }

}
