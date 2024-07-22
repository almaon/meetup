package com.example.meetup.meetings.domain.meeting;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import com.example.meetup.meetings.base.SystemClock;
import com.example.meetup.meetings.domain.MeetingGroupLocation;
import com.example.meetup.meetings.domain.MoneyValue;
import com.example.meetup.meetings.domain.base.TestBase;
import com.example.meetup.meetings.domain.meeting.Meeting;
import com.example.meetup.meetings.domain.meeting.MeetingLocation;
import com.example.meetup.meetings.domain.meeting.MeetingTerm;
import com.example.meetup.meetings.domain.meeting.Term;
import com.example.meetup.meetings.domain.meetingCommentingConfiguration.MeetingCommentingConfiguration;
import com.example.meetup.meetings.domain.meetingGroup.MeetingGroup;
import com.example.meetup.meetings.domain.meetingGroupProposal.MeetingGroupProposal;
import com.example.meetup.meetings.domain.member.MemberId;

import lombok.Getter;
import lombok.Setter;


public class MeetingTestBase extends TestBase {

    @Getter
    @Setter
    public static class MeetingTestDataOptions {

        private MemberId creatorId;

        private MeetingTerm meetingTerm;

        private Term rvspTerm;

        private Integer guestsLimit;

        private Integer attendeesLimit;

        private boolean isMeetingCommentingEnabled = true;

        private Iterable<MemberId> attendees = new ArrayList<>();

        private Iterable<MemberId> notAttendees = new ArrayList<>();

        public MeetingTestDataOptions(MemberId creatorId, MeetingTerm meetingTerm, Term rvspTerm, int guestsLimit, Integer attendeesLimit, boolean isMeetingCommentingEnabled, Iterable<MemberId> attendees, Iterable<MemberId> notAttendees) {
            this.creatorId = creatorId;
            this.meetingTerm = meetingTerm;
            this.rvspTerm = rvspTerm;
            this.guestsLimit = guestsLimit;
            this.attendeesLimit = attendeesLimit;
            this.isMeetingCommentingEnabled = isMeetingCommentingEnabled;
            this.attendees = attendees;
            this.notAttendees = notAttendees;
        }
    }

    public static class MeetingTestData {

        private MeetingGroup meetingGroup;

        private Meeting meeting;

        private MeetingCommentingConfiguration meetingCommentingConfiguration;

        public MeetingGroup getMeetingGroup() {
            return meetingGroup;
        }

        public Meeting getMeeting() {
            return meeting;
        }

        public MeetingCommentingConfiguration getMeetingCommentingConfiguration() {
            return meetingCommentingConfiguration;
        }

        public MeetingTestData(MeetingGroup meetingGroup,
                               Meeting meeting,
                               MeetingCommentingConfiguration meetingCommentingConfiguration) {
            this.meetingGroup = meetingGroup;
            this.meeting = meeting;
            this.meetingCommentingConfiguration = meetingCommentingConfiguration;
        }
    }

    public MeetingTestData createMeetingTestData(MeetingTestDataOptions options) {

        var proposalMemberId = options.creatorId != null ? options.creatorId
                : new MemberId(UUID.randomUUID());
        var meetingProposal = new MeetingGroupProposal("name", "description",
                new MeetingGroupLocation("Warsaw", "PL"), proposalMemberId);

        meetingProposal.accept();

        var meetingGroup = meetingProposal.createMeetingGroup();

        meetingGroup.setExpirationDate(SystemClock.now().plusDays(1));

        var meetingTerm = options.meetingTerm != null ? options.meetingTerm
                : new MeetingTerm(SystemClock.now().plusDays(1), SystemClock.now().plusDays(2));
        var rsvpTerm = options.rvspTerm != null ? options.rvspTerm
                : new Term(null, null);
        var meeting = meetingGroup.createMeeting(
                "title",
                meetingTerm,
                "description",
                new MeetingLocation("Name", "Address", "City", "Postalcode"),
                options.attendeesLimit,
                options.guestsLimit,
                rsvpTerm,
                MoneyValue.undefined(),
                new ArrayList<MemberId>(),
                proposalMemberId);

        for (var attendee : options.attendees) {
            meetingGroup.joinToGroupMember(attendee);
            meeting.addAttendee(meetingGroup, attendee, 0);
        }

        for (var notAttendee : options.notAttendees) {
            meetingGroup.joinToGroupMember(notAttendee);
            meeting.addNotAttendee(notAttendee);
        }

        var meetingCommentingConfiguration = meeting.createCommentingConfiguration();
        if (options.isMeetingCommentingEnabled) {
            meetingCommentingConfiguration.enableCommenting(proposalMemberId, meetingGroup);
        } else {
            meetingCommentingConfiguration.disableCommenting(proposalMemberId, meetingGroup);
        }

        clearPublishedDomainEventsList();

        return new MeetingTestData(meetingGroup, meeting, meetingCommentingConfiguration);
    }
}
