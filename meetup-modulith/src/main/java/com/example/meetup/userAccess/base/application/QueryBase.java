package com.example.meetup.userAccess.base.application;

import java.util.UUID;

import lombok.Getter;


@Getter
public abstract class QueryBase implements IQuery {

    private final UUID id;

    protected QueryBase() {
        id = UUID.randomUUID();
    }

    protected QueryBase(UUID id) {
        this.id = id;
    }
}
