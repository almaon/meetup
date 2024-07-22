package com.example.meetup.infrastructure;

import java.util.List;
import java.util.UUID;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.example.meetup.meetings.application.members.view.GetMemberByLoginQuery;
import com.example.meetup.meetings.base.SystemClock;
import com.example.meetup.meetings.domain.meeting.Meeting;
import com.example.meetup.meetings.domain.meeting.MeetingId;
import com.example.meetup.meetings.domain.meeting.events.MeetingCreatedEvent;
import com.example.meetup.meetings.domain.meetingCommentingConfiguration.MeetingCommentingConfiguration;
import com.example.meetup.meetings.domain.meetingCommentingConfiguration.MeetingCommentingConfigurationId;
import com.example.meetup.meetings.domain.meetingCommentingConfiguration.events.MeetingCommentingConfigurationCreatedEvent;
import com.example.meetup.meetings.domain.meetingCommentingConfiguration.events.MeetingCommentingEnabledEvent;
import com.example.meetup.meetings.domain.meetingGroup.MeetingGroup;
import com.example.meetup.meetings.domain.meetingGroup.MeetingGroupId;
import com.example.meetup.meetings.domain.meetingGroup.events.MeetingGroupCreatedEvent;
import com.example.meetup.meetings.domain.meetingGroup.events.MeetingGroupPaymentInfoUpdatedEvent;
import com.example.meetup.meetings.domain.meetingGroup.events.NewMeetingGroupMemberJoinedEvent;
import com.example.meetup.meetings.domain.meetingGroupProposal.MeetingGroupProposal;
import com.example.meetup.meetings.domain.meetingGroupProposal.MeetingGroupProposalId;
import com.example.meetup.meetings.domain.meetingGroupProposal.events.MeetingGroupProposedEvent;
import com.example.meetup.meetings.domain.member.Member;
import com.example.meetup.meetings.domain.member.MemberId;
import com.example.meetup.meetings.domain.member.events.MemberCreatedEvent;
import com.example.meetup.meetings.infrastructure.MeetingsQueryDispatcher;

import lombok.RequiredArgsConstructor;

@Profile("demo")
@Component
@RequiredArgsConstructor
public class DemoData implements CommandLineRunner {


	private final com.example.meetup.userAccess.base.domain.IAggregateStore userAccessAggregateStore;

	private final com.example.meetup.meetings.base.domain.IAggregateStore meetingsAggregateStore;

	private final com.example.meetup.administration.base.domain.IAggregateStore administrationAggregateStore;

	private final com.example.meetup.payments.base.domain.IAggregateStore paymentsAggregateStore;

	private final MeetingsQueryDispatcher meetingsQueryDispatcher;

	@Override
	public void run(String... args) {

		if (meetingsQueryDispatcher.executeQuery(new GetMemberByLoginQuery("user1")) != null) 
			return;

		var user1Id = UUID.fromString("e7de6763-3f6b-4c2a-8632-1f87316ec9eb");//UUID.randomUUID();
		var user1 = new Member();
		user1.setMemberId(new MemberId(user1Id));

		var user2Id = UUID.fromString("62207c85-e8e1-4406-bfd5-98f9d704b147");//UUID.randomUUID();
		var user2 = new Member();
		user2.setMemberId(new MemberId(user2Id));

		var meetingCommentingConfigurationId = UUID.randomUUID();
		var meetingCommentingConfigurationCommentingConfigA = new MeetingCommentingConfiguration();
		meetingCommentingConfigurationCommentingConfigA.setMeetingCommentingConfigurationId(new MeetingCommentingConfigurationId(meetingCommentingConfigurationId));

		var meetingAId = UUID.randomUUID();
		var meetingA = new Meeting();
		meetingA.setMeetingId(new MeetingId(meetingAId));
		
		var meetingBId = UUID.randomUUID();
		var meetingB = new Meeting();
		meetingB.setMeetingId(new MeetingId(meetingBId));

		var meetingGroupAId = UUID.randomUUID();
		var meetingGroupA = new MeetingGroup();
		meetingGroupA.setMeetingGroupId(new MeetingGroupId(meetingGroupAId));
		
		var meetingGroupBId = UUID.randomUUID();
		var meetingGroupB = new MeetingGroup();
		meetingGroupB.setMeetingGroupId(new MeetingGroupId(meetingGroupBId));

		var meetingGroupProposalAId = UUID.fromString("06af1ea3-5dbf-4916-a02d-bf37360a2437");//UUID.randomUUID();
		var meetingGroupProposalA = new MeetingGroupProposal();
		meetingGroupProposalA.setMeetingGroupProposalId(new MeetingGroupProposalId(meetingGroupProposalAId));

		var meetingGroupProposalBId = UUID.fromString("c88baef4-9a5d-44b8-960c-7ecbf2ab1242");//UUID.randomUUID();
		var meetingGroupProposalB = new MeetingGroupProposal();
		meetingGroupProposalB.setMeetingGroupProposalId(new MeetingGroupProposalId(meetingGroupProposalBId));

//		var adminMeetingGroupProposalA = 
//				new com.example.meetup.administration.domain.meetingGroupProposals.MeetingGroupProposal();
//		adminMeetingGroupProposalA.setMeetingGroupProposalId(
//				new com.example.meetup.administration.domain.meetingGroupProposals.MeetingGroupProposalId(meetingGroupProposalAId));
		
		user1.addDomainEvent(new MemberCreatedEvent(
				user1Id, 
				"user1@example.com", 
				"user1", 
				"user1", 
				"user1", 
				SystemClock.now()));
		user1.incrementVersion();
		meetingsAggregateStore.save(user1);
		user1.clearDomainEvents();

		user2.addDomainEvent(new MemberCreatedEvent(
				user2Id, 
				"user2@example.com", 
				"user2", 
				"user2", 
				"user2", 
				SystemClock.now()));
		user2.incrementVersion();
		meetingsAggregateStore.save(user2);
		user2.clearDomainEvents();

		meetingGroupA.addDomainEvent(new MeetingGroupCreatedEvent(
				meetingGroupAId, 
				user2Id, 
				"MeetingGroup for testing", 
				"New York City", 
				"meeting Group A", 
				"NY"));
		meetingGroupA.incrementVersion();
		meetingsAggregateStore.save(meetingGroupA);
		meetingGroupA.clearDomainEvents();
		
		meetingGroupA.addDomainEvent(new NewMeetingGroupMemberJoinedEvent(
				meetingGroupAId, 
				user2Id, 
				"Organizer",
				SystemClock.now(),
				false));
		meetingGroupA.incrementVersion();
		meetingsAggregateStore.save(meetingGroupA);
		meetingGroupA.clearDomainEvents();
		
		meetingGroupA.addDomainEvent(new NewMeetingGroupMemberJoinedEvent(
				meetingGroupAId, 
				user1Id, 
				"Organizer",
				SystemClock.now(),
				false));
		meetingGroupA.incrementVersion();
		meetingsAggregateStore.save(meetingGroupA);
		meetingGroupA.clearDomainEvents();

		meetingGroupB.addDomainEvent(new MeetingGroupCreatedEvent(
				meetingGroupBId, 
				user2Id, 
				"MeetingGroup for testing B", 
				"New York City B", 
				"meeting Group B", 
				"NYB"));
		meetingGroupB.incrementVersion();
		meetingsAggregateStore.save(meetingGroupB);
		meetingGroupB.clearDomainEvents();

		meetingGroupB.addDomainEvent(new NewMeetingGroupMemberJoinedEvent(
				meetingGroupBId, 
				user2Id, 
				"Organizer",
				SystemClock.now(),
				false));
		meetingGroupB.incrementVersion();
		meetingsAggregateStore.save(meetingGroupB);
		meetingGroupB.clearDomainEvents();	

		meetingGroupA.addDomainEvent(new MeetingGroupPaymentInfoUpdatedEvent(
				meetingGroupAId, 
				SystemClock.now().plusDays(2)));
		meetingGroupA.incrementVersion();
		meetingsAggregateStore.save(meetingGroupA);
		meetingGroupA.clearDomainEvents();

		meetingA.addDomainEvent(new MeetingCreatedEvent(
				meetingAId,
				"Meeting 1",
				"Meeting 1 Desciption",
				SystemClock.now().plusHours(2),
				SystemClock.now().plusHours(3),
				"street XY",
				"Cityhall",
				"New York City", 
				"NY", 
				SystemClock.now(),
				SystemClock.now().plusHours(2), 
				2, 
				100, 
				"USD", 
				List.of(user2Id), 
				meetingGroupAId, 
				SystemClock.now(), 
				3));
		meetingA.incrementVersion();
		meetingsAggregateStore.save(meetingA);
		meetingA.clearDomainEvents();

		meetingCommentingConfigurationCommentingConfigA.addDomainEvent(new MeetingCommentingConfigurationCreatedEvent(
				meetingAId, 
				false, 
				meetingCommentingConfigurationId));
		meetingCommentingConfigurationCommentingConfigA.incrementVersion();
		meetingsAggregateStore.save(meetingCommentingConfigurationCommentingConfigA);
		meetingCommentingConfigurationCommentingConfigA.clearDomainEvents();

		meetingCommentingConfigurationCommentingConfigA.addDomainEvent(new MeetingCommentingEnabledEvent(meetingAId));
		meetingCommentingConfigurationCommentingConfigA.incrementVersion();
		meetingsAggregateStore.save(meetingCommentingConfigurationCommentingConfigA);
		meetingCommentingConfigurationCommentingConfigA.clearDomainEvents();
		
		meetingGroupProposalA.addDomainEvent(new MeetingGroupProposedEvent(
				meetingGroupProposalAId, 
				"Meeting Group C description", 
				"London", 
				"Meeting Group C", 
				"UK", 
				user2Id, 
				SystemClock.now()));
		meetingGroupProposalA.incrementVersion();
		meetingsAggregateStore.save(meetingGroupProposalA);
		meetingGroupProposalA.clearDomainEvents();
		
		meetingGroupProposalB.addDomainEvent(new MeetingGroupProposedEvent(
				meetingGroupProposalBId, 
				"Meeting Group D description", 
				"London", 
				"Meeting Group D", 
				"UK", 
				user2Id, 
				SystemClock.now()));
		meetingGroupProposalB.incrementVersion();
		meetingsAggregateStore.save(meetingGroupProposalB);
		meetingGroupProposalB.clearDomainEvents();
	}

}
