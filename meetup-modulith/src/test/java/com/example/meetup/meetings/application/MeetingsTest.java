package com.example.meetup.meetings.application;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.awaitility.Awaitility;
import org.awaitility.core.ConditionTimeoutException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.test.context.ActiveProfiles;

import com.example.meetup.meetings.application.meetingCommentingConfigurations.CreateMeetingCommentingConfigurationCommand;
import com.example.meetup.meetings.application.meetingCommentingConfigurations.EnableMeetingCommentingConfigurationCommand;
import com.example.meetup.meetings.application.meetingComments.AddMeetingCommentCommand;
import com.example.meetup.meetings.application.meetingComments.AddMeetingCommentLikeCommand;
import com.example.meetup.meetings.application.meetingComments.view.GetMeetingCommentLikersByIdQuery;
import com.example.meetup.meetings.application.meetingComments.view.GetMeetingCommentsByIdQuery;
import com.example.meetup.meetings.application.meetingComments.view.MeetingCommentLikersView;
import com.example.meetup.meetings.application.meetingComments.view.MeetingCommentsView;
import com.example.meetup.meetings.application.meetingGroupProposals.AcceptMeetingGroupProposalCommand;
import com.example.meetup.meetings.application.meetingGroupProposals.ProposeMeetingGroupCommand;
import com.example.meetup.meetings.application.meetingGroups.JoinToGroupCommand;
import com.example.meetup.meetings.application.meetingGroups.view.GetMeetingGroupsByIdQuery;
import com.example.meetup.meetings.application.meetingGroups.view.MeetingGroupsView;
import com.example.meetup.meetings.application.meetings.AddMeetingAttendeeCommand;
import com.example.meetup.meetings.application.meetings.CreateMeetingCommand;
import com.example.meetup.meetings.application.meetings.view.GetMeetingAttendeesByIdQuery;
import com.example.meetup.meetings.application.meetings.view.GetMeetingsByIdQuery;
import com.example.meetup.meetings.application.meetings.view.MeetingAttendeesView;
import com.example.meetup.meetings.application.meetings.view.MeetingsView;
import com.example.meetup.meetings.base.SystemClock;
import com.example.meetup.meetings.base.domain.IAggregateStore;
import com.example.meetup.meetings.base.infrastructure.ICommandDispatcher;
import com.example.meetup.meetings.base.infrastructure.IQueryDispatcher;
import com.example.meetup.meetings.domain.meeting.Meeting;
import com.example.meetup.meetings.domain.meeting.MeetingId;
import com.example.meetup.meetings.domain.meeting.events.MeetingAttendeeAddedEvent;
import com.example.meetup.meetings.domain.meeting.events.MeetingCreatedEvent;
import com.example.meetup.meetings.domain.meetingComment.events.MeetingCommentAddedEvent;
import com.example.meetup.meetings.domain.meetingCommentingConfiguration.MeetingCommentingConfiguration;
import com.example.meetup.meetings.domain.meetingCommentingConfiguration.MeetingCommentingConfigurationId;
import com.example.meetup.meetings.domain.meetingCommentingConfiguration.events.MeetingCommentingConfigurationCreatedEvent;
import com.example.meetup.meetings.domain.meetingCommentingConfiguration.events.MeetingCommentingEnabledEvent;
import com.example.meetup.meetings.domain.meetingGroup.MeetingGroup;
import com.example.meetup.meetings.domain.meetingGroup.MeetingGroupId;
import com.example.meetup.meetings.domain.meetingGroup.events.MeetingGroupCreatedEvent;
import com.example.meetup.meetings.domain.meetingGroup.events.MeetingGroupPaymentInfoUpdatedEvent;
import com.example.meetup.meetings.domain.meetingGroup.events.NewMeetingGroupMemberJoinedEvent;
import com.example.meetup.meetings.domain.meetingGroupProposal.events.MeetingGroupProposalAcceptedEvent;
import com.example.meetup.meetings.domain.meetingGroupProposal.events.MeetingGroupProposedEvent;
import com.example.meetup.meetings.domain.meetingMemberCommentLike.events.MeetingCommentLikedEvent;
import com.example.meetup.meetings.domain.member.Member;
import com.example.meetup.meetings.domain.member.MemberId;
import com.example.meetup.meetings.domain.member.events.MemberCreatedEvent;
import com.example.meetup.meetings.infrastructure.MeetingContext;

@ActiveProfiles("test")
@SpringBootTest//(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MeetingsTest {

	
	@MockBean
	private OAuth2AuthorizedClientManager auth2AuthorizedClientManagerMOCK;
	
	@Autowired
	protected ICommandDispatcher commandDispatcher;
	
	@Autowired
	protected IQueryDispatcher queryDispatcher;
	
	@Autowired
	protected IAggregateStore aggregateStore;
	
    @BeforeEach
    protected void setUpApplicationEventDispatcher() {
        
        SystemClock.set(LocalDateTime.parse("2024-02-16T08:29:11.601"));
    }

	
	@Test
	public void commentingConfiguration() {
						
		// given -------------------------------------------------------------------------
		
		var memberAId = UUID.randomUUID();
		var memberMemberA = new Member();
		memberMemberA.setMemberId(new MemberId(memberAId));
	

		var meetingAId = UUID.randomUUID();
		var meetingMeetingA = new Meeting();
		meetingMeetingA.setMeetingId(new MeetingId(meetingAId));
		
		var meetingGroupAId = UUID.randomUUID();
		var meetingGroupGroupA = new MeetingGroup();
		meetingGroupGroupA.setMeetingGroupId(new MeetingGroupId(meetingGroupAId));		
		
		memberMemberA.addDomainEvent(new MemberCreatedEvent(
				memberAId, 
				"JohnDoe@example.com", 
				"JohnDoe", 
				"John", 
				"Doe", 
				SystemClock.now()));
		memberMemberA.incrementVersion();
		aggregateStore.save(memberMemberA);
		memberMemberA.clearDomainEvents();
	
		meetingGroupGroupA.addDomainEvent(new MeetingGroupCreatedEvent(
				meetingGroupAId, 
				memberAId, 
				"MeetingGroup for testing", 
				"New York", 
				"meeting Group A", 
				"NYC"));
		meetingGroupGroupA.incrementVersion();
		aggregateStore.save(meetingGroupGroupA);
		meetingGroupGroupA.clearDomainEvents();
		
		meetingGroupGroupA.addDomainEvent(new NewMeetingGroupMemberJoinedEvent(
				meetingGroupAId, 
				memberAId, 
				"Organizer", 
				SystemClock.now(),
				false));
		meetingGroupGroupA.incrementVersion();
		aggregateStore.save(meetingGroupGroupA);
		meetingGroupGroupA.clearDomainEvents();
			
		meetingGroupGroupA.addDomainEvent(new MeetingGroupPaymentInfoUpdatedEvent(
				meetingGroupAId, 
				SystemClock.now().plusMonths(1)));
		meetingGroupGroupA.incrementVersion();
		aggregateStore.save(meetingGroupGroupA);
		meetingGroupGroupA.clearDomainEvents();
		
		meetingMeetingA.addDomainEvent(new MeetingCreatedEvent(
				meetingAId, 
				"Meeting A", 
				"a Meeting for testing", 
				SystemClock.now().plusHours(3),
				SystemClock.now().plusHours(4), 
				"street XY",
				"Cityhall",
				"New York City",
				"NY",
				SystemClock.now(),
				SystemClock.now().plusHours(2),  
				10, 
				12.34, 
				"USD", 
				List.of(memberAId), 
				meetingGroupAId, 
				SystemClock.now(), 
				30));
		meetingMeetingA.incrementVersion();
		aggregateStore.save(meetingMeetingA);
		meetingMeetingA.clearDomainEvents();
		
		// when -------------------------------------------------------------------------
		MeetingContext.setPrinicpalUUIDForTesting(memberAId);
		
		commandDispatcher.executeCommandAsync(new CreateMeetingCommentingConfigurationCommand(meetingAId));
		
		commandDispatcher.executeCommandAsync(new EnableMeetingCommentingConfigurationCommand(meetingAId));
		
		// then -------------------------------------------------------------------------
		try {
			Awaitility.await().atMost(1, TimeUnit.SECONDS).until(
					() -> aggregateStore.getDomainEvents().stream().filter(event -> event instanceof MeetingCommentingConfigurationCreatedEvent)
						.map(event -> (MeetingCommentingConfigurationCreatedEvent) event).toList(),
					events -> events.stream().anyMatch(event -> {
						return meetingAId.equals(event.getMeetingId())
						&& !event.getIsEnabled();}));
		} catch (ConditionTimeoutException e) {
			fail("no matching MeetingCreatedEvent found");
		}
		
		try {
			Awaitility.await().atMost(1, TimeUnit.SECONDS).until(
					() -> aggregateStore.getDomainEvents().stream().filter(event -> event instanceof MeetingCommentingEnabledEvent)
						.map(event -> (MeetingCommentingEnabledEvent) event).toList(),
					events -> events.stream().anyMatch(event ->
						meetingAId.equals(event.getMeetingId())));
		} catch (ConditionTimeoutException e) {
			fail("no matching MeetingCreatedEvent found");
		}
		
		aggregateStore.clearDomainEvents();
		SystemClock.reset();

	}

	@Test
	public void createMeeting() {
								
		// given -------------------------------------------------------------------------
		
		var memberAId = UUID.randomUUID();
		var memberMemberA = new Member();
		memberMemberA.setMemberId(new MemberId(memberAId));
		
		var meetingGroupAId = UUID.randomUUID();
		var meetingGroupGroupA = new MeetingGroup();
		meetingGroupGroupA.setMeetingGroupId(new MeetingGroupId(meetingGroupAId));		
		
		memberMemberA.addDomainEvent(new MemberCreatedEvent(
				memberAId, 
				"JohnDoe@example.com", 
				"JohnDoe", 
				"John", 
				"Doe", 
				SystemClock.now()));
		memberMemberA.incrementVersion();
		aggregateStore.save(memberMemberA);
		memberMemberA.clearDomainEvents();
	
		meetingGroupGroupA.addDomainEvent(new MeetingGroupCreatedEvent(
				meetingGroupAId, 
				memberAId, 
				"MeetingGroup for testing", 
				"New York", 
				"meeting Group A", 
				"NYC"));
		meetingGroupGroupA.incrementVersion();
		aggregateStore.save(meetingGroupGroupA);
		meetingGroupGroupA.clearDomainEvents();
		
		meetingGroupGroupA.addDomainEvent(new NewMeetingGroupMemberJoinedEvent(
				meetingGroupAId, 
				memberAId, 
				"Organizer", 
				SystemClock.now(),
				false));
		meetingGroupGroupA.incrementVersion();
		aggregateStore.save(meetingGroupGroupA);
		meetingGroupGroupA.clearDomainEvents();
			
		meetingGroupGroupA.addDomainEvent(new MeetingGroupPaymentInfoUpdatedEvent(
				meetingGroupAId, 
				SystemClock.now().plusMonths(1)));
		meetingGroupGroupA.incrementVersion();
		aggregateStore.save(meetingGroupGroupA);
		meetingGroupGroupA.clearDomainEvents();
		
		// when -------------------------------------------------------------------------
		
		MeetingContext.setPrinicpalUUIDForTesting(memberAId);

		UUID meetingId = commandDispatcher.executeCommandSync(new CreateMeetingCommand(
				meetingGroupAId, 
				"a Meeting for testing", 
				SystemClock.now().plusHours(3),
				"NY",
				"Meeting A", 
				"street XY",
				12.34, 
				"New York City",
				"USD", 
				30,
				"Cityhall",
				SystemClock.now().plusHours(2),  
				SystemClock.now(),
				SystemClock.now().plusHours(4), 
				10, 
				List.of(memberAId)));
		
		// then -------------------------------------------------------------------------
		
		try {
			Awaitility.await().atMost(1, TimeUnit.SECONDS).until(
					() -> aggregateStore.getDomainEvents().stream().filter(event -> event instanceof MeetingCreatedEvent)
						.map(event -> (MeetingCreatedEvent) event).toList(),
					events -> events.stream().anyMatch(event ->
						meetingId.equals(event.getMeetingId())
						&& "Meeting A".equals(event.getTitle())
						&& "a Meeting for testing".equals(event.getDescription())
						&& SystemClock.now().plusHours(3).equals(event.getTermStartDate())
						&& SystemClock.now().plusHours(4).equals(event.getTermEndDate())
						&& "street XY".equals(event.getMeetingLocationAddress())
						&& "Cityhall".equals(event.getMeetingLocationName())
						&& "New York City".equals(event.getMeetingLocationCity())
						&& "NY".equals(event.getMeetingLocationPostalCode())
						&& SystemClock.now().equals(event.getRsvpTermStartDate())
						&& SystemClock.now().plusHours(2).equals(event.getRsvpTermEndDate())
						&& 10 == event.getGuestsLimit()
						&& 12.34 == event.getEventFeeValue()
						&& "USD".equals(event.getEventFeeCurrency())
						&& List.of(memberAId).containsAll(event.getHostMemberIds())
						&& meetingGroupAId.equals(event.getMeetingGroupId())
						&& SystemClock.now().equals(event.getCreateDate())));
		} catch (ConditionTimeoutException e) {
			fail("no matching MeetingCreatedEvent found");
		}

		try {
			var meeting = Awaitility.await().atMost(1, TimeUnit.SECONDS).until(
					() -> (MeetingsView) queryDispatcher.executeQuery(new GetMeetingsByIdQuery(meetingId)),
					_meeting -> _meeting != null && meetingId.equals(_meeting.getMeetingId()));

			assertThat("title  is equals", "Meeting A".equals(meeting.getTitle()));
			assertThat("description  is equals", "a Meeting for testing".equals(meeting.getDescription()));
			assertThat("meetingLocationPostalCode  is equals", "NY".equals(meeting.getMeetingLocationPostalCode()));
			assertThat("meetingLocationAddress  is equals", "street XY".equals(meeting.getMeetingLocationAddress()));
			assertThat("meetingLocationCity  is equals", "New York City".equals(meeting.getMeetingLocationCity()));
			assertThat("termStartDate  is equals", SystemClock.now().plusHours(3).equals(meeting.getTermStartDate()));
			assertThat("termEndDate  is equals", SystemClock.now().plusHours(4).equals(meeting.getTermEndDate()));
		} catch (ConditionTimeoutException e) {
			fail("no meetingView found");
		}
		
		aggregateStore.clearDomainEvents();
		SystemClock.reset();

	}

	@Test
	public void createMeetingGroup() {
						
		// given -------------------------------------------------------------------------
		
		var memberAId = UUID.randomUUID();
		var memberMemberA = new Member();
		memberMemberA.setMemberId(new MemberId(memberAId));
		
		memberMemberA.addDomainEvent(new MemberCreatedEvent(
				memberAId, 
				"JohnDoe@example.com", 
				"JohnDoe", 
				"John", 
				"Doe", 
				SystemClock.now()));
		memberMemberA.incrementVersion();
		aggregateStore.save(memberMemberA);
		memberMemberA.clearDomainEvents();
	
		
		// when -------------------------------------------------------------------------
		
		MeetingContext.setPrinicpalUUIDForTesting(memberAId);
		
		UUID proposeMeetingGroupId = commandDispatcher.executeCommandSync(
				new ProposeMeetingGroupCommand(
						"meeting A", 
						"the first meeting", 
						"New York", 
						"NY"));
		
		commandDispatcher.executeCommandAsync(new AcceptMeetingGroupProposalCommand(proposeMeetingGroupId));
		
		// then -------------------------------------------------------------------------

		try {
			Awaitility.await().atMost(1, TimeUnit.SECONDS).until(
					() -> aggregateStore.getDomainEvents().stream().filter(event -> event instanceof MeetingGroupProposedEvent)
						.map(event -> (MeetingGroupProposedEvent) event).toList(),
					events -> events.stream().anyMatch(event -> 
						proposeMeetingGroupId.equals(event.getMeetingGroupProposalId())
						&& "meeting A".equals(event.getName())
						&& "the first meeting".equals(event.getDescription())
						&& "New York".equals(event.getLocationCity())
						&& "NY".equals(event.getLocationCountryCode())
						&& memberAId.equals(event.getProposalUserId())
						&& SystemClock.now().equals(event.getProposalDate())));
		} catch (ConditionTimeoutException e) {
			fail("no matching MeetingGroupProposedEvent found");
		}
		
		try {
			Awaitility.await().atMost(1, TimeUnit.SECONDS).until(
					() -> aggregateStore.getDomainEvents().stream().filter(event -> event instanceof MeetingGroupProposalAcceptedEvent)
						.map(event -> (MeetingGroupProposalAcceptedEvent) event).toList(),
					events -> events.stream().anyMatch(event -> 
						proposeMeetingGroupId.equals(event.getMeetingGroupProposalId())));
		} catch (ConditionTimeoutException e) {
			fail("no matching MeetingGroupProposalAcceptedEvent found");
		}
	
		try {
			Awaitility.await().atMost(1, TimeUnit.SECONDS).until(
					() -> aggregateStore.getDomainEvents().stream().filter(event -> event instanceof MeetingGroupCreatedEvent)
						.map(event -> (MeetingGroupCreatedEvent) event).toList(),
					events -> events.stream().anyMatch(event -> 
						proposeMeetingGroupId.equals(event.getMeetingGroupId())
						&& memberAId.equals(event.getCreatorId())
						&& "meeting A".equals(event.getName())
						&& "the first meeting".equals(event.getDescription())
						&& "New York".equals(event.getLocationCity())
						&& "NY".equals(event.getLocationCountryCode())));
		} catch (ConditionTimeoutException e) {
			fail("no matching MeetingGroupCreatedEvent found");
		}

		try {
			Awaitility.await().atMost(1, TimeUnit.SECONDS).until(
					() -> aggregateStore.getDomainEvents().stream().filter(event -> event instanceof NewMeetingGroupMemberJoinedEvent)
						.map(event -> (NewMeetingGroupMemberJoinedEvent) event).toList(),
					events -> events.stream().anyMatch(event -> 
						proposeMeetingGroupId.equals(event.getMeetingGroupId())
						&& memberAId.equals(event.getMemberId())
						&& "Organizer".equals(event.getRole())));
		} catch (ConditionTimeoutException e) {
			fail("no matching NewMeetingGroupMemberJoinedEvent found");
		}

		try {
			var meetingGroup = Awaitility.await().atMost(1, TimeUnit.SECONDS).until(
					() -> (MeetingGroupsView) queryDispatcher.executeQuery(new GetMeetingGroupsByIdQuery((UUID) proposeMeetingGroupId)),
					_meetingGroup -> _meetingGroup != null && proposeMeetingGroupId.equals(_meetingGroup.getMeetingGroupId()));

			assertThat("name is equals", "meeting A".equals(meetingGroup.getName()));
			assertThat("description is equals", "the first meeting".equals(meetingGroup.getDescription()));
			assertThat("city is equals", "New York".equals(meetingGroup.getLocationCity()));
			assertThat("countryCode is equals", "NY".equals(meetingGroup.getLocationCountryCode()));
			assertThat("memberCount is equals", 1 == meetingGroup.getMemberCount());
		} catch (ConditionTimeoutException e) {
			fail("no meetingGroupView found");
		}
		
		aggregateStore.clearDomainEvents();
		SystemClock.reset();
	
	}
	
	
	@Test
	public void comment() {
						
		// given -------------------------------------------------------------------------
		
		var memberAId = UUID.randomUUID();
		var memberMemberA = new Member();
		memberMemberA.setMemberId(new MemberId(memberAId));
	
		var memberBId = UUID.randomUUID();
		var memberMemberB = new Member();
		memberMemberB.setMemberId(new MemberId(memberBId));
		
		var meetingCommentingConfigurationId = UUID.randomUUID();
		var meetingCommentingConfigurationCommentingConfigA = new MeetingCommentingConfiguration();
		meetingCommentingConfigurationCommentingConfigA.setMeetingCommentingConfigurationId(
				new MeetingCommentingConfigurationId(meetingCommentingConfigurationId));
		
		var meetingAId = UUID.randomUUID();
		var meetingMeetingA = new Meeting();
		meetingMeetingA.setMeetingId(new MeetingId(meetingAId));
		
		var meetingGroupAId = UUID.randomUUID();
		var meetingGroupGroupA = new MeetingGroup();
		meetingGroupGroupA.setMeetingGroupId(new MeetingGroupId(meetingGroupAId));
		
		memberMemberA.addDomainEvent(new MemberCreatedEvent(
				memberAId, 
				"JohnDoe@example.com", 
				"JohnDoe", 
				"John", 
				"Doe", 
				SystemClock.now()));
		memberMemberA.incrementVersion();
		aggregateStore.save(memberMemberA);
		memberMemberA.clearDomainEvents();
	
		meetingGroupGroupA.addDomainEvent(new MeetingGroupCreatedEvent(
				meetingGroupAId, 
				memberAId, 
				"MeetingGroup for testing", 
				"New York", 
				"meeting Group A", 
				"NYC"));
		meetingGroupGroupA.incrementVersion();
		aggregateStore.save(meetingGroupGroupA);
		meetingGroupGroupA.clearDomainEvents();
		
		meetingGroupGroupA.addDomainEvent(new NewMeetingGroupMemberJoinedEvent(
				meetingGroupAId, 
				memberAId, 
				"Organizer", 
				SystemClock.now(),
				false));
		meetingGroupGroupA.incrementVersion();
		aggregateStore.save(meetingGroupGroupA);
		meetingGroupGroupA.clearDomainEvents();
			
		meetingGroupGroupA.addDomainEvent(new MeetingGroupPaymentInfoUpdatedEvent(
				meetingGroupAId, 
				SystemClock.now().plusMonths(1)));
		meetingGroupGroupA.incrementVersion();
		aggregateStore.save(meetingGroupGroupA);
		meetingGroupGroupA.clearDomainEvents();
		
		meetingMeetingA.addDomainEvent(new MeetingCreatedEvent(
				meetingAId, 
				"Meeting A", 
				"a Meeting for testing", 
				SystemClock.now().plusHours(3),
				SystemClock.now().plusHours(4), 
				"street XY",
				"Cityhall",
				"New York City",
				"NY",
				SystemClock.now(),
				SystemClock.now().plusHours(2),  
				10, 
				12.34, 
				"USD", 
				List.of(memberAId), 
				meetingGroupAId, 
				SystemClock.now(), 
				30));
		meetingMeetingA.incrementVersion();
		aggregateStore.save(meetingMeetingA);
		meetingMeetingA.clearDomainEvents();
		
		meetingCommentingConfigurationCommentingConfigA.addDomainEvent(new MeetingCommentingConfigurationCreatedEvent(
				meetingAId,
				false,
				meetingCommentingConfigurationId));
		meetingCommentingConfigurationCommentingConfigA.incrementVersion();
		aggregateStore.save(meetingCommentingConfigurationCommentingConfigA);
		meetingCommentingConfigurationCommentingConfigA.clearDomainEvents();

		meetingCommentingConfigurationCommentingConfigA.addDomainEvent(new MeetingCommentingEnabledEvent(
				meetingAId));
		meetingCommentingConfigurationCommentingConfigA.incrementVersion();
		aggregateStore.save(meetingCommentingConfigurationCommentingConfigA);
		meetingCommentingConfigurationCommentingConfigA.clearDomainEvents();

		memberMemberB.addDomainEvent(new MemberCreatedEvent(
				memberBId, 
				"maxM@example.com", 
				"Max", 
				"Max", 
				"Mustermann", 
				SystemClock.now()));
		memberMemberB.incrementVersion();
		aggregateStore.save(memberMemberB);
		memberMemberB.clearDomainEvents();
		
		// when -------------------------------------------------------------------------
		
		MeetingContext.setPrinicpalUUIDForTesting(memberBId);
		SystemClock.set(SystemClock.now().plusMinutes(30));
		
		commandDispatcher.executeCommandAsync(new JoinToGroupCommand(meetingGroupAId));
				
		commandDispatcher.executeCommandAsync(new AddMeetingAttendeeCommand(meetingAId, 5));
		
		UUID meetingCommentId = commandDispatcher.executeCommandSync(
				new AddMeetingCommentCommand(meetingAId, "the comment"));
		
		commandDispatcher.executeCommandAsync(new AddMeetingCommentLikeCommand(meetingCommentId));
		
		// then -------------------------------------------------------------------------
		
		try {
			Awaitility.await().atMost(1, TimeUnit.SECONDS).until(
					() -> aggregateStore.getDomainEvents().stream().filter(event -> event instanceof MeetingAttendeeAddedEvent)
					.map(event -> (MeetingAttendeeAddedEvent) event).toList(),
					events -> events.stream().anyMatch(event -> {
						return meetingAId.equals(event.getMeetingId())
								&& memberBId.equals(event.getAttendeeId())
								&& SystemClock.now().equals(event.getRsvpDate())
								&& "Attendee".equals(event.getRole())
								&& 5 == event.getGuestsNumber()
								&& 12.34 == event.getFeeValue()
								&& "USD".equals(event.getFeeCurrency());}));
		} catch (ConditionTimeoutException e) {
			fail("no matching MeetingCreatedEvent found");
		}

		try {
			Awaitility.await().atMost(1, TimeUnit.SECONDS).until(
					() -> aggregateStore.getDomainEvents().stream().filter(event -> event instanceof MeetingCommentAddedEvent)
					.map(event -> (MeetingCommentAddedEvent) event).toList(),
					events -> events.stream().anyMatch(event -> {
						return meetingCommentId.equals(event.getMeetingCommentId())
								&& meetingAId.equals(event.getMeetingId())
								&& "the comment".equals(event.getComment())
								&& memberBId.equals(event.getAuthorId())
								&& SystemClock.now().equals(event.getCreateDate());}));
		} catch (ConditionTimeoutException e) {
			fail("no matching MeetingCreatedEvent found");
		}		

		try {
			Awaitility.await().atMost(1, TimeUnit.SECONDS).until(
					() -> aggregateStore.getDomainEvents().stream().filter(event -> event instanceof MeetingCommentLikedEvent)
					.map(event -> (MeetingCommentLikedEvent) event).toList(),
					events -> events.stream().anyMatch(event -> {
						return meetingCommentId.equals(event.getMeetingCommentId())
								&& memberBId.equals(event.getLikerId());}));
		} catch (ConditionTimeoutException e) {
			fail("no matching MeetingCreatedEvent found");
		}		

		try {
			var meetingAttenddees = Awaitility.await().atMost(1, TimeUnit.SECONDS).until(
					() -> (MeetingAttendeesView) queryDispatcher.executeQuery(new GetMeetingAttendeesByIdQuery(meetingAId)),
					_meetingAttendees -> _meetingAttendees != null && meetingAId.equals(_meetingAttendees.getMeetingId()));

			var attendee = meetingAttenddees.getAttendees().get(0);

			assertThat("memberId  is equals", memberBId.equals(attendee.getMemberId()));
			assertThat("name  is equals", "Max".equals(attendee.getName()));
			assertThat("lastName  is equals", "Mustermann".equals(attendee.getLastName()));
			assertThat("firstName  is equals", "Max".equals(attendee.getFirstName()));
			assertThat("email  is equals", "maxM@example.com".equals(attendee.getEmail()));
			assertThat("gustNumber  is equals", 5 == attendee.getGustNumber());
			assertThat("isFeePaid  is equals", false == attendee.getIsFeePaid());
		} catch (ConditionTimeoutException e) {
			fail("no meetingAttenddeesView found");
		}
		
		try {
			var meetingComment = Awaitility.await().atMost(1, TimeUnit.SECONDS).until(
					() -> (MeetingCommentsView) queryDispatcher.executeQuery(new GetMeetingCommentsByIdQuery((UUID) meetingCommentId)),
					_meetingComment -> _meetingComment != null);

			assertThat("inReplyToCommentId  is equals", null == meetingComment.getInReplyToCommentId());
			assertThat("authorId  is equals", memberBId.equals(meetingComment.getAuthorId()));
			assertThat("comment  is equals", "the comment".equals(meetingComment.getComment()));
			assertThat("createDate  is equals", SystemClock.now().equals(meetingComment.getCreateDate()));
			assertThat("editDate  is equals", null == meetingComment.getEditDate());
			assertThat("likesCount  is equals", 1 == meetingComment.getLikesCount());
		} catch (ConditionTimeoutException e) {
			fail("no meetingCommentView found");
		}
		
		
		try {
			var meetingCommentLikers = Awaitility.await().atMost(1, TimeUnit.SECONDS).until(
					() -> (MeetingCommentLikersView) queryDispatcher.executeQuery(new GetMeetingCommentLikersByIdQuery((UUID) meetingCommentId)),
					_meetingCommentLikers -> _meetingCommentLikers != null);

			var meetingCommentLiker = meetingCommentLikers.getMeetingCommentLikers().get(0);
			
			assertThat("likerId  is equals", memberBId.equals(meetingCommentLiker.getLikerId()));
			assertThat("name  is equals", "Max".equals(meetingCommentLiker.getName()));
		} catch (ConditionTimeoutException e) {
			fail("no MeetingCommentLikersView found");
		}

		
		aggregateStore.clearDomainEvents();
	}
}