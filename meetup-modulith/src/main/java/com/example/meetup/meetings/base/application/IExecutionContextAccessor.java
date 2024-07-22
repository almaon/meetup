package com.example.meetup.meetings.base.application;

import java.util.UUID;

public interface IExecutionContextAccessor {

    UUID getUserId();

    UUID getCorrelationId();

    boolean isAvailable();
}
