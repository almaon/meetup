package com.example.meetup.meetings.domain.meeting;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.example.meetup.meetings.base.SystemClock;
import com.example.meetup.meetings.domain.meeting.MeetingTerm;
import com.example.meetup.meetings.domain.meeting.events.MeetingAttendeeRemovedEvent;
import com.example.meetup.meetings.domain.meeting.events.MeetingCanceledEvent;
import com.example.meetup.meetings.domain.meeting.rules.MeetingCannotBeChangedAfterStartRule;
import com.example.meetup.meetings.domain.meeting.rules.OnlyActiveAttendeeCanBeRemovedFromMeetingRule;
import com.example.meetup.meetings.domain.meeting.rules.ReasonOfRemovingAttendeeFromMeetingMustBeProvidedRule;
import com.example.meetup.meetings.domain.member.MemberId;


public class MeetingTests extends MeetingTestBase {

	@Test
    public void cancelMeeting_whenMeetingHasNotStarted_isSuccessful() {
        var creatorId = new MemberId(UUID.randomUUID());
        var meetingTestData = createMeetingTestData(new MeetingTestDataOptionsBuilder()
                .setCreatorId(creatorId).createMeetingTestDataOptions());

        meetingTestData.getMeeting().cancel(creatorId);

        publishedDomainEvents.addAll(meetingTestData.getMeeting().getDomainEvents());
        
        var meetingCanceled = assertPublishedDomainEvent(MeetingCanceledEvent.class);

        assertThat(meetingCanceled.getMeetingId(), equalTo(meetingTestData.getMeeting().getMeetingId().getValue()));
        assertThat(meetingCanceled.getCancelMemberId(), equalTo(creatorId.getValue()));
        assertThat(meetingCanceled.getCancelDate(), equalTo(SystemClock.now()));
    }

    @Test
    public void cancelMeeting_whenMeetingHasStarted_isNotPossible() {
        var creatorId = new MemberId(UUID.randomUUID());
        var meetingTerm = new MeetingTerm(SystemClock.now().plusHours(1), SystemClock.now().plusHours(3));
        var meetingTestData = createMeetingTestData(new MeetingTestDataOptionsBuilder()
                .setCreatorId(creatorId)
                .setMeetingTerm(meetingTerm)
                .createMeetingTestDataOptions());

        SystemClock.set(SystemClock.now().plusHours(2));

        assertBrokenRule(() -> meetingTestData.getMeeting().cancel(creatorId),
                MeetingCannotBeChangedAfterStartRule.class);

        assertThat(meetingTestData.getMeeting().isCanceled(), equalTo(false));
    }

    @Test
    public void removeAttendee_whenMeetingHasStarted_isNotPossible() {
        var creatorId = new MemberId(UUID.randomUUID());
        var meetingTerm = new MeetingTerm(SystemClock.now().plusHours(1), SystemClock.now().plusHours(3));

        var meetingTestData = createMeetingTestData(
                new MeetingTestDataOptionsBuilder()
                    .setCreatorId(creatorId)
                    .setMeetingTerm(meetingTerm)
                    .createMeetingTestDataOptions());

        SystemClock.set(SystemClock.now().plusHours(2));

        assertBrokenRule(
                () -> meetingTestData.getMeeting().removeAttendee(creatorId, creatorId, "reason", 1)
                , MeetingCannotBeChangedAfterStartRule.class);

        assertThat(meetingTestData.getMeeting().getAttendees()
                .stream()
                .map(attendee -> attendee.getAttendeeId())
                .toList(), hasItem(creatorId));
    }

    @Test
    public void removeAttendee_whenMemberIsNotAttendee_breaksOnlyActiveAttendeeCanBeRemovedFromMeetingRule() {
        var creatorId = new MemberId(UUID.randomUUID());
        var attendeeId = new MemberId(UUID.randomUUID());

        var meetingTestData = createMeetingTestData(
                new MeetingTestDataOptionsBuilder()
                    .setCreatorId(creatorId)
                    .createMeetingTestDataOptions());

        assertBrokenRule(
                () -> meetingTestData.getMeeting().removeAttendee(attendeeId, creatorId, "reason", 1),
                OnlyActiveAttendeeCanBeRemovedFromMeetingRule.class);
    }

    @Test
    public void removeAttendee_whenMemberIsAttendee_andReasonIsProvided_isSuccessful() {
        var creatorId = new MemberId(UUID.randomUUID());

        var meetingTestData = createMeetingTestData(
                new MeetingTestDataOptionsBuilder()
                        .setCreatorId(creatorId)
                        .createMeetingTestDataOptions());

        meetingTestData.getMeeting().removeAttendee(creatorId, creatorId, "reason", 1);

        publishedDomainEvents.addAll(meetingTestData.getMeeting().getDomainEvents());
        meetingTestData.getMeeting().clearDomainEvents();
        
        assertPublishedDomainEvent(MeetingAttendeeRemovedEvent.class);

        assertThat(meetingTestData.getMeeting().getAttendees()
                .stream()
                .map(attendee -> attendee.getAttendeeId())
                .toList(), hasItem(creatorId));
    }

    @Test
    public void removeAttendee_whenMemberIsAttendee_andReasonIsNotProvided_breaksReasonOfRemovingAttendeeFromMeetingMustBeProvidedRule() {
        var creatorId = new MemberId(UUID.randomUUID());
        var meetingTestData = createMeetingTestData(new MeetingTestDataOptionsBuilder()
                .setCreatorId(creatorId)
                .createMeetingTestDataOptions());

        assertBrokenRule(() -> meetingTestData.getMeeting().removeAttendee(creatorId, creatorId, null, 1),
                ReasonOfRemovingAttendeeFromMeetingMustBeProvidedRule.class);
    }
}