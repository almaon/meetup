package com.example.meetup.userAccess.base.application;

import java.util.UUID;

public interface IExecutionContextAccessor {

    UUID getUserId();

    UUID getCorrelationId();

    boolean isAvailable();
}
