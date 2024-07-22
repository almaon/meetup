package com.example.meetup.meetings.domain.meetingGroupProposal;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.example.meetup.meetings.domain.MeetingGroupLocation;
import com.example.meetup.meetings.domain.base.TestBase;
import com.example.meetup.meetings.domain.meetingGroup.MeetingGroupMemberRole;
import com.example.meetup.meetings.domain.meetingGroup.events.MeetingGroupCreatedEvent;
import com.example.meetup.meetings.domain.meetingGroup.events.NewMeetingGroupMemberJoinedEvent;
import com.example.meetup.meetings.domain.meetingGroupProposal.events.MeetingGroupProposalAcceptedEvent;
import com.example.meetup.meetings.domain.meetingGroupProposal.events.MeetingGroupProposedEvent;
import com.example.meetup.meetings.domain.meetingGroupProposal.rules.MeetingGroupProposalCannotBeAcceptedMoreThanOnceRule;
import com.example.meetup.meetings.domain.member.MemberId;


public class MeetingGroupProposalTests extends TestBase {

    @Test
    public void ProposeNewMeetingGroup_IsSuccessful() {
        var proposalMemberId = new MemberId(UUID.randomUUID());
        var meetingGroupProposal = new MeetingGroupProposal(
                "name",
                "description",
                new MeetingGroupLocation(
                        "Hannover",
                        "GER"),
                proposalMemberId);

        publishedDomainEvents.addAll(meetingGroupProposal.getDomainEvents());

        var meetingGroupProposed = assertPublishedDomainEvent(MeetingGroupProposedEvent.class);

        assertThat(meetingGroupProposed.getProposalUserId(), equalTo(proposalMemberId.getValue()));
    }

    @Test
    public void acceptProposal_whenIsNotAccepted_isSuccessful() {
        var proposalMemberId = new MemberId(UUID.randomUUID());
        var meetingGroupProposal = new MeetingGroupProposal(
                "name",
                "description",
                new MeetingGroupLocation(
                        "Hannover",
                        "GER"),
                proposalMemberId);

        meetingGroupProposal.accept();
        
        publishedDomainEvents.addAll(meetingGroupProposal.getDomainEvents());

        var meetingGroupProposalAccepted =
                assertPublishedDomainEvent(MeetingGroupProposalAcceptedEvent.class);

        assertThat(meetingGroupProposalAccepted.getMeetingGroupProposalId(),
                equalTo(meetingGroupProposal.getMeetingGroupProposalId().getValue()));
    }

    @Test
    public void acceptProposal_whenIsAlreadyAccepted_breaksProposalCannotBeAcceptedMoreThanOnceRule() {
        var proposalMemberId = new MemberId(UUID.randomUUID());
        var meetingGroupProposal = new MeetingGroupProposal(
                "name",
                "description",
                new MeetingGroupLocation(
                        "Hannover",
                        "GER"),
                proposalMemberId);

        meetingGroupProposal.accept();

        publishedDomainEvents.addAll(meetingGroupProposal.getDomainEvents());

        assertBrokenRule(
                () -> meetingGroupProposal.accept(),
                MeetingGroupProposalCannotBeAcceptedMoreThanOnceRule.class);
    }

    @Test
    public void createMeetingGroup_isSuccessful_and_creatorIsAHost() {
        var proposalMemberId = new MemberId(UUID.randomUUID());
        var name = "name";
        var description = "description";
        var meetingGroupLocation = new MeetingGroupLocation("Warsaw", "PL");
        var meetingGroupProposal = new MeetingGroupProposal(
                name,
                description,
                meetingGroupLocation,
                proposalMemberId);

        var meetingGroup = meetingGroupProposal.createMeetingGroup();

        publishedDomainEvents.addAll(meetingGroup.getDomainEvents());

        var meetingGroupCreated =
                assertPublishedDomainEvent(MeetingGroupCreatedEvent.class);
        var newMeetingGroupMemberJoined =
                assertPublishedDomainEvent(NewMeetingGroupMemberJoinedEvent.class);

        assertThat(meetingGroupCreated.getMeetingGroupId(), equalTo(meetingGroupProposal.getMeetingGroupProposalId().getValue()));
        assertThat(newMeetingGroupMemberJoined.getMemberId(), equalTo(proposalMemberId.getValue()));
        assertThat(newMeetingGroupMemberJoined.getRole(), equalTo(MeetingGroupMemberRole.Organizer.name()));
    }
}
