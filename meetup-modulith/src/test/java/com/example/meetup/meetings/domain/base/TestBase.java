package com.example.meetup.meetings.domain.base;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.isA;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import com.example.meetup.meetings.base.SystemClock;
import com.example.meetup.meetings.base.domain.BusinessRuleValidationException;
import com.example.meetup.meetings.base.domain.IDomainEvent;

public class TestBase {

    public List<IDomainEvent> publishedDomainEvents;

    
    protected void clearPublishedDomainEventsList() {
        publishedDomainEvents.clear();
    }

    @BeforeEach
    protected void setUpApplicationEventDispatcher() {

        publishedDomainEvents = new ArrayList<>();
        
        SystemClock.set(LocalDateTime.parse("2024-02-16T08:29:11.601"));
    }

    @AfterEach
    public void afterEachTest() {
        SystemClock.reset();
    }

    protected  <T extends IDomainEvent> T assertPublishedDomainEvent(Class<T> type) {

        return (T) publishedDomainEvents.stream()
                .filter(event -> event.getClass().isAssignableFrom(type))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(type + " not published"));
    }

    protected <T> void assertDomainEventNotPublished(Class<T> type) {
        publishedDomainEvents.stream()
                .filter(event -> event.getClass().isAssignableFrom(type))
                .findAny()
                .ifPresent(a -> new RuntimeException(type + " event shall not be published"));
    }

    public static <TRule> void assertBrokenRule(TestDelegate testDelegate, Class<TRule> ruleType) {
        var message = "Expected " + ruleType.getName() + " broken rule";

        BusinessRuleValidationException businessRuleValidationException = assertThrows(
                BusinessRuleValidationException.class, () -> testDelegate.run());

        assertThat(message, businessRuleValidationException.getBrokenRule(), isA(ruleType));
    }

    public static interface TestDelegate {
        void run();
    }
}
