package com.example.meetup.meetings.base.application;

import lombok.Getter;

import java.util.UUID;


@Getter
public abstract class CommandBase implements ICommand {

    private final UUID id;

    protected CommandBase() {
        id = UUID.randomUUID();
    }

    protected CommandBase(UUID id) {
        this.id = id;
    }
}
