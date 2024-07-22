package com.example.meetup.meetings.domain.meeting;
import java.util.ArrayList;

import com.example.meetup.meetings.domain.meeting.MeetingTerm;
import com.example.meetup.meetings.domain.meeting.Term;
import com.example.meetup.meetings.domain.member.MemberId;

public class MeetingTestDataOptionsBuilder {
	
    private MemberId creatorId;
    private MeetingTerm meetingTerm;
    private Term rvspTerm;
    private int guestsLimit;
    private int attendeesLimit;
    private boolean isMeetingCommentingEnabled = true;
    private Iterable<MemberId> attendees = new ArrayList<>();
    private Iterable<MemberId> notAttendees = new ArrayList<>();

    public MeetingTestDataOptionsBuilder setCreatorId(MemberId creatorId) {
        this.creatorId = creatorId;
        return this;
    }

    public MeetingTestDataOptionsBuilder setMeetingTerm(MeetingTerm meetingTerm) {
        this.meetingTerm = meetingTerm;
        return this;
    }

    public MeetingTestDataOptionsBuilder setRvspTerm(Term rvspTerm) {
        this.rvspTerm = rvspTerm;
        return this;
    }

    public MeetingTestDataOptionsBuilder setGuestsLimit(int guestsLimit) {
        this.guestsLimit = guestsLimit;
        return this;
    }

    public MeetingTestDataOptionsBuilder setAttendeesLimit(Integer attendeesLimit) {
        this.attendeesLimit = attendeesLimit;
        return this;
    }

    public MeetingTestDataOptionsBuilder setIsMeetingCommentingEnabled(boolean isMeetingCommentingEnabled) {
        this.isMeetingCommentingEnabled = isMeetingCommentingEnabled;
        return this;
    }

    public MeetingTestDataOptionsBuilder setAttendees(Iterable<MemberId> attendees) {
        this.attendees = attendees;
        return this;
    }

    public MeetingTestDataOptionsBuilder setNotAttendees(Iterable<MemberId> notAttendees) {
        this.notAttendees = notAttendees;
        return this;
    }

    public MeetingTestBase.MeetingTestDataOptions createMeetingTestDataOptions() {
        return new MeetingTestBase.MeetingTestDataOptions(creatorId, meetingTerm, rvspTerm, guestsLimit, attendeesLimit, isMeetingCommentingEnabled, attendees, notAttendees);
    }
}