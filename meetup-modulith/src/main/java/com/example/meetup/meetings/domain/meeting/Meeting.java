package com.example.meetup.meetings.domain.meeting;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.example.meetup.meetings.base.SystemClock;
import com.example.meetup.meetings.base.domain.Aggregate;
import com.example.meetup.meetings.base.domain.Entity;
import com.example.meetup.meetings.base.domain.IDomainEvent;
import com.example.meetup.meetings.domain.MoneyValue;
import com.example.meetup.meetings.domain.meeting.events.MeetingAttendeeAddedEvent;
import com.example.meetup.meetings.domain.meeting.events.MeetingCanceledEvent;
import com.example.meetup.meetings.domain.meeting.events.MeetingCreatedEvent;
import com.example.meetup.meetings.domain.meeting.events.MeetingMainAttributesChangedEvent;
import com.example.meetup.meetings.domain.meeting.events.MeetingNotAttendeeAddedEvent;
import com.example.meetup.meetings.domain.meeting.events.MeetingWaitlistMemberAddedEvent;
import com.example.meetup.meetings.domain.meeting.rules.AttendeeCanBeAddedOnlyInRsvpTermRule;
import com.example.meetup.meetings.domain.meeting.rules.AttendeesLimitCannotBeChangedToSmallerThanActiveAttendeesRule;
import com.example.meetup.meetings.domain.meeting.rules.MeetingAttendeeMustBeAMemberOfGroupRule;
import com.example.meetup.meetings.domain.meeting.rules.MeetingAttendeesNumberIsAboveLimitRule;
import com.example.meetup.meetings.domain.meeting.rules.MeetingCannotBeChangedAfterStartRule;
import com.example.meetup.meetings.domain.meeting.rules.MeetingGuestsNumberIsAboveLimitRule;
import com.example.meetup.meetings.domain.meeting.rules.MeetingMustHaveAtLeastOneHostRule;
import com.example.meetup.meetings.domain.meeting.rules.MemberCannotBeAnAttendeeOfMeetingMoreThanOnceRule;
import com.example.meetup.meetings.domain.meeting.rules.MemberCannotBeMoreThanOnceOnMeetingWaitlistRule;
import com.example.meetup.meetings.domain.meeting.rules.MemberCannotBeNotAttendeeTwiceRule;
import com.example.meetup.meetings.domain.meeting.rules.MemberOnWaitlistMustBeAMemberOfGroupRule;
import com.example.meetup.meetings.domain.meeting.rules.NotActiveMemberOfWaitlistCannotBeSignedOffRule;
import com.example.meetup.meetings.domain.meeting.rules.NotActiveNotAttendeeCannotChangeDecisionRule;
import com.example.meetup.meetings.domain.meeting.rules.OnlyActiveAttendeeCanBeRemovedFromMeetingRule;
import com.example.meetup.meetings.domain.meeting.rules.OnlyMeetingAttendeeCanHaveChangedRoleRule;
import com.example.meetup.meetings.domain.meeting.rules.OnlyMeetingOrGroupOrganizerCanSetMeetingMemberRolesRule;
import com.example.meetup.meetings.domain.meetingComment.MeetingComment;
import com.example.meetup.meetings.domain.meetingCommentingConfiguration.MeetingCommentingConfiguration;
import com.example.meetup.meetings.domain.meetingGroup.MeetingGroup;
import com.example.meetup.meetings.domain.meetingGroup.MeetingGroupId;
import com.example.meetup.meetings.domain.member.MemberId;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Meeting extends Aggregate {

	// business id
	@Setter // for testing
	protected MeetingId meetingId;

	public String getStreamId() {
		return "Meetings-Meeting-" + meetingId.getValue();
	}

	protected MeetingGroupId meetingGroupId;
	protected String title;
	protected MeetingTerm term;
	protected String description;
	protected MeetingLocation location;
	protected List<MeetingAttendee> attendees = new ArrayList<>();
	protected List<MeetingNotAttendee> notAttendees = new ArrayList<>();
	protected List<MeetingWaitlistMember> waitlistMembers = new ArrayList<>();
	protected MeetingLimits meetingLimits;
	protected Term rsvpTerm;
	protected MoneyValue eventFee;
	protected MemberId creatorId;
	protected LocalDateTime createDate;
	protected MemberId changeMemberId;
	protected LocalDateTime changeDate;
	protected LocalDateTime cancelDate;
	protected MemberId cancelMemberId;
	protected boolean isCanceled;

	public Meeting() {
	}

	public Meeting(MeetingGroupId meetingGroupId, String title, MeetingTerm term, String description, MeetingLocation location, MeetingLimits meetingLimits, Term rsvpTerm, MoneyValue eventFee, List<MemberId> hostsMembersIds, MemberId creatorId) {
		var meetingCreated = new MeetingCreatedEvent(
				UUID.randomUUID(),
				title,
				description,
				term.getStartDate(),
				term.getEndDate(),
				location.getAddress(),
				location.getName(),
				location.getCity(),
				location.getPostalCode(),
				rsvpTerm.getStartDate(),
				rsvpTerm.getEndDate(),
				meetingLimits.getGuestsLimit(),
				eventFee.getValue(),
				eventFee.getCurrency(),
				new ArrayList<>(),
				meetingGroupId.getValue(),
				SystemClock.now(),
				meetingLimits.getAttendeesLimit());

		apply(meetingCreated);
		addDomainEvent(meetingCreated);

		if (hostsMembersIds != null && !hostsMembersIds.isEmpty()) {
			for (var hostMemberId : hostsMembersIds) {

				var hostAddedEvent = new MeetingAttendeeAddedEvent(
						meetingCreated.getMeetingId(),
						hostMemberId.getValue(),
						meetingCreated.getCreateDate(),
						MeetingAttendeeRole.Host.name(),
						0,
						meetingCreated.getEventFeeValue(),
						meetingCreated.getEventFeeCurrency());

				apply(hostAddedEvent);
				addDomainEvent(hostAddedEvent);
			}
		} else {
			var hostAddedEvent = new MeetingAttendeeAddedEvent(
					meetingCreated.getMeetingId(),
					creatorId.getValue(),
					meetingCreated.getCreateDate(),
					MeetingAttendeeRole.Host.name(),
					0,
					0,
					null);

			apply(hostAddedEvent);
			addDomainEvent(hostAddedEvent);
		}
	}

	public void changeMainAttributes(String title, MeetingTerm term, String description, MeetingLocation location, MeetingLimits meetingLimits, Term rsvpTerm, MoneyValue eventFee, MemberId modifyUserId) {
		checkRule(new AttendeesLimitCannotBeChangedToSmallerThanActiveAttendeesRule(
                meetingLimits,
                getAllActiveAttendeesWithGuestsNumber()));
		
		var meetingMainAttributesChanged = new MeetingMainAttributesChangedEvent(
				meetingId.getValue(),
				title,
				location.getPostalCode(),
				eventFee.getValue(),
				eventFee.getCurrency(),
				rsvpTerm.getEndDate(),
				location.getName(),
				term.getStartDate(),
				description,
				location.getAddress(),
				location.getCity(),
				term.getEndDate(),
				rsvpTerm.getStartDate(),
				meetingLimits.getGuestsLimit(),
				meetingLimits.getAttendeesLimit());

		apply(meetingMainAttributesChanged);
		addDomainEvent(meetingMainAttributesChanged);
	}

	public void addAttendee(MeetingGroup meetingGroup, MemberId attendeeId, int guestsNumber) {
		checkRule(new MeetingCannotBeChangedAfterStartRule(term));
		checkRule(new AttendeeCanBeAddedOnlyInRsvpTermRule(rsvpTerm));
		checkRule(new MeetingAttendeeMustBeAMemberOfGroupRule(attendeeId, meetingGroup));
		checkRule(new MemberCannotBeAnAttendeeOfMeetingMoreThanOnceRule(attendeeId, attendees));
		checkRule(new MeetingGuestsNumberIsAboveLimitRule(meetingLimits.getGuestsLimit(), guestsNumber));
		checkRule(new MeetingAttendeesNumberIsAboveLimitRule(meetingLimits.getAttendeesLimit(), getAllActiveAttendeesWithGuestsNumber(), guestsNumber));

		var notAttendee = getActiveNotAttendee(attendeeId);
		if (notAttendee != null)
			notAttendee.changeDecision();

		var meetingAttendeeAdded = new MeetingAttendeeAddedEvent(
				meetingId.getValue(),
				attendeeId.getValue(),
				SystemClock.now(),
				MeetingAttendeeRole.Attendee.name(),
				guestsNumber,
				eventFee.getValue(),
				eventFee.getCurrency());

		apply(meetingAttendeeAdded);
		addDomainEvent(meetingAttendeeAdded);
	}

	public void addNotAttendee(MemberId memberId) {
        checkRule(new MeetingCannotBeChangedAfterStartRule(term));
        checkRule(new MemberCannotBeNotAttendeeTwiceRule(notAttendees, memberId));
        
		var attendee = getActiveAttendee(memberId);

		if (attendee != null) 
			attendee.changeDecision();

		var meetingNotAttendeeAdded = new MeetingNotAttendeeAddedEvent(
				meetingId.getValue(),
				memberId.getValue());

		apply(meetingNotAttendeeAdded);
		addDomainEvent(meetingNotAttendeeAdded);
		
		var nextWaitlistMember = waitlistMembers.stream()
				.filter(m -> m.isActive())
				.sorted((m1, m2) -> m1.getSignUpDate().
						compareTo(m2.getSignUpDate()))
				.findFirst().orElse(null);

		if (nextWaitlistMember != null) {
			var meetingAttendeeAdded = new MeetingAttendeeAddedEvent(
					meetingId.getValue(),
					nextWaitlistMember.getMemberId().getValue(),
					SystemClock.now(),
					MeetingAttendeeRole.Attendee.name(),
					0,  // TODO: save guest number in waitinglist
					eventFee.getValue(),
					eventFee.getCurrency());
			nextWaitlistMember.markIsMovedToAttendees(SystemClock.now());

			apply(meetingAttendeeAdded);
			addDomainEvent(meetingAttendeeAdded);
		}

	}

	public void signUpMemberToWaitlist(MeetingGroup meetingGroup, MemberId memberId) {
        checkRule(new MeetingCannotBeChangedAfterStartRule(term));
        checkRule(new AttendeeCanBeAddedOnlyInRsvpTermRule(rsvpTerm));
        checkRule(new MemberOnWaitlistMustBeAMemberOfGroupRule(meetingGroup, memberId, attendees));
        checkRule(new MemberCannotBeMoreThanOnceOnMeetingWaitlistRule(waitlistMembers, memberId));
        
		var meetingWaitlistMemberAdded = new MeetingWaitlistMemberAddedEvent(
				meetingId.getValue(),
				memberId.getValue(),
				SystemClock.now());

		apply(meetingWaitlistMemberAdded);
		addDomainEvent(meetingWaitlistMemberAdded);
	}

	public void cancel(MemberId cancelMemberId) {
        checkRule(new MeetingCannotBeChangedAfterStartRule(term));

		if (!isCanceled) {
			var meetingCanceled = new MeetingCanceledEvent(
					meetingId.getValue(),
					cancelMemberId.getValue(),
					SystemClock.now());

			apply(meetingCanceled);
			addDomainEvent(meetingCanceled);			
		}
	}

	public void changeNotAttendeeDecision(MemberId memberId) {
        checkRule(new MeetingCannotBeChangedAfterStartRule(term));
        checkRule(new NotActiveNotAttendeeCannotChangeDecisionRule(notAttendees, memberId));
        
		var notAttendee = notAttendees.stream().filter(na -> na.isActiveNotAttendee(memberId)).findAny().get();

		notAttendee.changeDecision();
	}

	public void signOffMemberFromWaitlist(MemberId memberId) {
        checkRule(new MeetingCannotBeChangedAfterStartRule(term));
        checkRule(new NotActiveMemberOfWaitlistCannotBeSignedOffRule(waitlistMembers, memberId));
        
		var memberOnWaitlist = getActiveMemberOnWaitlist(memberId);

		memberOnWaitlist.signOff();
	}

	public void setHostRole(MeetingGroup meetingGroup, MemberId settingMemberId, MemberId newOrganizerId) {
        checkRule(new MeetingCannotBeChangedAfterStartRule(term));
        checkRule(new OnlyMeetingOrGroupOrganizerCanSetMeetingMemberRolesRule(settingMemberId, meetingGroup, attendees));
        checkRule(new OnlyMeetingAttendeeCanHaveChangedRoleRule(attendees, newOrganizerId));
        
		var attendee = getActiveAttendee(newOrganizerId);

		attendee.setAsHost();
	}

	public void setAttendeeRole(MeetingGroup meetingGroup, MemberId settingMemberId, MemberId newAttendeeId) {
        checkRule(new MeetingCannotBeChangedAfterStartRule(term));
        checkRule(new OnlyMeetingOrGroupOrganizerCanSetMeetingMemberRolesRule(settingMemberId, meetingGroup, attendees));
        checkRule(new OnlyMeetingAttendeeCanHaveChangedRoleRule(attendees, newAttendeeId));
        
		var attendee = getActiveAttendee(newAttendeeId);

		var meetingHostNumber = attendees.stream().filter(x -> x.isActiveHost() && !x.getAttendeeId().equals(newAttendeeId)).count();
		checkRule(new MeetingMustHaveAtLeastOneHostRule(meetingHostNumber));

		attendee.setAsAttendee();
	}	
	public void removeAttendee(MemberId attendeeId, MemberId removingPersonId, String reason, int guestsNumber) {
        checkRule(new MeetingCannotBeChangedAfterStartRule(term));
        checkRule(new OnlyActiveAttendeeCanBeRemovedFromMeetingRule(attendees, attendeeId));
        
		var attendee = getActiveAttendee(attendeeId);
		attendee.remove(removingPersonId, reason, guestsNumber);
	}

	public void markAttendeeFeeAsPayed(MemberId memberId) {
		var attendee = getActiveAttendee(memberId);

		attendee.markFeeAsPayed();
	}

	public MeetingComment addComment(MemberId authorId, String comment, MeetingGroup meetingGroup, MeetingCommentingConfiguration meetingCommentingConfiguration) {
		return new MeetingComment(
				meetingId, 
				authorId, 
				comment, 
				null, 
				meetingCommentingConfiguration, 
				meetingGroup);
	}

	public MeetingCommentingConfiguration createCommentingConfiguration() {
		return new MeetingCommentingConfiguration(meetingId);
	}	

	private MeetingWaitlistMember getActiveMemberOnWaitlist(MemberId memberId) {
		return waitlistMembers.stream().filter(wm -> wm.isActiveOnWaitList(memberId)).findAny().orElse(null);
	}	

	private MeetingAttendee getActiveAttendee(MemberId attendeeId) {
		return attendees.stream().filter(_attendee -> _attendee.isActiveAttendee(attendeeId)).findAny().orElse(null);
	}	

	public MeetingNotAttendee getActiveNotAttendee(MemberId memberId) {
		return notAttendees.stream().filter(notAttendee -> notAttendee.isActiveNotAttendee(memberId)).findAny().orElse(null);
	}
	
	public MeetingNotAttendee getNotAttendee(MemberId memberId) {
		return notAttendees.stream().filter(notAttendee -> notAttendee.getMemberId().equals(memberId)).findAny().orElse(null);
	}

	private int getAllActiveAttendeesWithGuestsNumber() {
        return attendees.stream()
        		.filter(a -> a.isActive())
        		.map(a -> a.getAttendeeWithGuestsNumber())
        		.reduce(0, (nr1, nr2) -> nr1 + nr2);

	}	

	private void setRsvpTerm(Term rsvpTerm, MeetingTerm meetingTerm) {
	}	


	private boolean when(MeetingAttendeeAddedEvent event) {
		attendees.add(
				new MeetingAttendee(
						this, 
						new MeetingId(event.getMeetingId()), 
						new MemberId(event.getAttendeeId()), 
						event.getRsvpDate(), 
						MeetingAttendeeRole.valueOf(event.getRole()), 
						event.getGuestsNumber(),
						new MoneyValue(event.getFeeValue(), event.getFeeCurrency())));
		return true;
	}

	private boolean when(MeetingMainAttributesChangedEvent event) {
		title = event.getTitle();
		description = event.getDescription();
		location = new MeetingLocation(
				event.getMeetingLocationName(), 
				event.getMeetingLocationAddress(), 
				event.getMeetingLocationPostalCode(), 
				event.getMeetingLocationCity());
		eventFee = new MoneyValue(event.getEventFeeValue(), event.getEventFeeCurrency());
		rsvpTerm = new Term(event.getRsvpTermStartDate(), event.getRsvpTermEndDate());
		term = new MeetingTerm(event.getTermStartDate(), event.getTermEndDate());
		meetingLimits = new MeetingLimits(event.getAttendeesLimit(), event.getGuestsLimit());

		return true;
	}

	private boolean when(MeetingNotAttendeeAddedEvent event) {
		notAttendees.add(
				new MeetingNotAttendee(
						this, 
						meetingId, 
						new MemberId(event.getMemberId())));
		return true;
	}

	private boolean when(MeetingWaitlistMemberAddedEvent event) {
		waitlistMembers.add(
				new MeetingWaitlistMember(
						this, 
						meetingId, 
						new MemberId(event.getMemberId()),
						event.getSignUpDate()));
		return true;
	}

	private boolean when(MeetingCanceledEvent event) {
		isCanceled = true;
		cancelDate = event.getCancelDate();

		return true;
	}

	private boolean when(MeetingCreatedEvent event) {
		meetingId = new MeetingId(event.getMeetingId());
		title = event.getTitle();
		description = event.getDescription();
		meetingGroupId = new MeetingGroupId(event.getMeetingGroupId());
		location = new MeetingLocation(
				event.getMeetingLocationName(), 
				event.getMeetingLocationAddress(), 
				event.getMeetingLocationPostalCode(), 
				event.getMeetingLocationCity());
		createDate = event.getCreateDate();
		eventFee = new MoneyValue(event.getEventFeeValue(), event.getEventFeeCurrency());
		rsvpTerm = new Term(event.getRsvpTermStartDate(), event.getRsvpTermEndDate());
		term = new MeetingTerm(event.getTermStartDate(), event.getTermEndDate());
		meetingLimits = new MeetingLimits(event.getAttendeesLimit(), event.getGuestsLimit());

		return true;
	}

	@Override
	protected boolean apply(IDomainEvent event) {

		var processed = false;
		
		if (event instanceof MeetingAttendeeAddedEvent castedEvent) {
			processed = when(castedEvent);
		}
		else if (event instanceof MeetingMainAttributesChangedEvent castedEvent) {
			processed = when(castedEvent);
		}
		else if (event instanceof MeetingNotAttendeeAddedEvent castedEvent) {
			processed = when(castedEvent);
		}
		else if (event instanceof MeetingWaitlistMemberAddedEvent castedEvent) {
			processed = when(castedEvent);
		}
		else if (event instanceof MeetingCanceledEvent castedEvent) {
			processed = when(castedEvent);
		}
		else if (event instanceof MeetingCreatedEvent castedEvent) {
			processed = when(castedEvent);
		}
		
		if (processed)
			incrementVersion();
			
		return processed;
	}

	@Override
	public List<Entity> getDirectChildEntities() {
		var entities = new ArrayList<Entity>();
		entities.addAll(attendees);
		entities.addAll(notAttendees);
		entities.addAll(waitlistMembers);
		return entities;
	}


}
