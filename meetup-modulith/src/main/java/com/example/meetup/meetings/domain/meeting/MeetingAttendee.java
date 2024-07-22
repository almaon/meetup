package com.example.meetup.meetings.domain.meeting;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.meetup.meetings.base.SystemClock;
import com.example.meetup.meetings.base.domain.Aggregate;
import com.example.meetup.meetings.base.domain.Entity;
import com.example.meetup.meetings.base.domain.IDomainEvent;
import com.example.meetup.meetings.domain.MoneyValue;
import com.example.meetup.meetings.domain.meeting.events.MeetingAttendeeChangedDecisionEvent;
import com.example.meetup.meetings.domain.meeting.events.MeetingAttendeeFeePaidEvent;
import com.example.meetup.meetings.domain.meeting.events.MeetingAttendeeRemovedEvent;
import com.example.meetup.meetings.domain.meeting.events.MemberSetAsAttendeeEvent;
import com.example.meetup.meetings.domain.meeting.events.NewMeetingHostSetEvent;
import com.example.meetup.meetings.domain.meeting.rules.MemberCannotHaveSetAttendeeRoleMoreThanOnceRule;
import com.example.meetup.meetings.domain.meeting.rules.ReasonOfRemovingAttendeeFromMeetingMustBeProvidedRule;
import com.example.meetup.meetings.domain.member.MemberId;

import lombok.Getter;

@Getter
public class MeetingAttendee extends Entity {

	// business id
	protected MemberId attendeeId;
	protected MeetingId meetingId;
	protected LocalDateTime decisionDate;
	protected MeetingAttendeeRole role;
	protected int guestsNumber;
	protected boolean decisionChanged;
	protected LocalDateTime decisionChangeDate;
	protected LocalDateTime removedDate;
	protected MemberId removingMemberId;
	protected String removingReason;
	protected boolean isRemoved;
	protected MoneyValue fee;
	protected boolean isFeePaid;

	MeetingAttendee(Aggregate root, MeetingId meetingId, MemberId attendeeId, LocalDateTime decisionDate, MeetingAttendeeRole role, int guestsNumber, MoneyValue eventFee) {
		super(root);

		this.attendeeId = attendeeId;
		this.meetingId = meetingId;
		this.decisionDate = decisionDate;
		this.role = role;
		this.guestsNumber = guestsNumber;
		this.fee = eventFee;
	}

	void changeDecision() {
		var meetingAttendeeChangedDecision = new MeetingAttendeeChangedDecisionEvent(
				meetingId.getValue(),
				attendeeId.getValue(), 
				SystemClock.now()); 

		apply(meetingAttendeeChangedDecision);
		addDomainEvent(meetingAttendeeChangedDecision);
	}

	void setAsHost() {
		var newMeetingHostSet = new NewMeetingHostSetEvent(attendeeId.getValue(), meetingId.getValue());

		apply(newMeetingHostSet);
		addDomainEvent(newMeetingHostSet);
	}

	void setAsAttendee() {
        checkRule(new MemberCannotHaveSetAttendeeRoleMoreThanOnceRule(role));

		var memberSetAsAttendee = new MemberSetAsAttendeeEvent(meetingId.getValue(), attendeeId.getValue());

		apply(memberSetAsAttendee);
		addDomainEvent(memberSetAsAttendee);
	}

	void remove(MemberId removingMemberId, String reason, int guestsNumber) {
		checkRule(new ReasonOfRemovingAttendeeFromMeetingMustBeProvidedRule(reason));
		
		var meetingAttendeeRemoved = new MeetingAttendeeRemovedEvent(
				meetingId.getValue(),
				attendeeId.getValue(),
				reason,
				guestsNumber,
				SystemClock.now(),
				removingMemberId.getValue()); 

		apply(meetingAttendeeRemoved);
		addDomainEvent(meetingAttendeeRemoved);
	}

	void markFeeAsPayed() {
		var meetingAttendeeFeePaid = new MeetingAttendeeFeePaidEvent(
				meetingId.getValue(), 
				attendeeId.getValue(), 
				SystemClock.now());

		apply(meetingAttendeeFeePaid);
		addDomainEvent(meetingAttendeeFeePaid);
	}

	boolean isActive() {
		return decisionChangeDate == null && !isRemoved;
	}

	public boolean isActiveHost() {
		return isActive() && role == MeetingAttendeeRole.Host;
	}	

	int getAttendeeWithGuestsNumber() {
		return 1 + guestsNumber;
	}

	public boolean isActiveAttendee(MemberId attendeeId) {
		return this.attendeeId.equals(attendeeId) && !decisionChanged;
	}	


	private boolean when(NewMeetingHostSetEvent event) {
		if (new MemberId(event.getHostId()).equals(attendeeId)) {
			role = MeetingAttendeeRole.Host;

			return true;
		}
		return false;
	}

	private boolean when(MemberSetAsAttendeeEvent event) {
		if (new MemberId(event.getMemberId()).equals(attendeeId)) {
			role = MeetingAttendeeRole.Attendee;

			return true;
		}
		return false;
	}

	private boolean when(MeetingAttendeeFeePaidEvent event) {
		if (new MemberId(event.getAttendeeId()).equals(attendeeId)) {
			isFeePaid = true;

			return true;
		}
		return false;
	}

	private boolean when(MeetingAttendeeChangedDecisionEvent event) {
		if (new MemberId(event.getMemberId()).equals(attendeeId)) {
			decisionChanged = true;

			return true;
		}
		return false;
	}

	private boolean when(MeetingAttendeeRemovedEvent event) {
		if (new MemberId(event.getAttendeeId()).equals(attendeeId)) {
			isRemoved = true;
			removedDate = event.getRemovedDate();
			removingReason = event.getReason();
			removingMemberId = new MemberId(event.getRemovingMemberId());

			return true;
		}
		return false;
	}

	@Override
	protected boolean apply(IDomainEvent event) {

		var processed = false;

		if (event instanceof NewMeetingHostSetEvent castedEvent) {
			processed = when(castedEvent);
		}
		else if (event instanceof MemberSetAsAttendeeEvent castedEvent) {
			processed = when(castedEvent);
		}
		else if (event instanceof MeetingAttendeeFeePaidEvent castedEvent) {
			processed = when(castedEvent);
		}
		else if (event instanceof MeetingAttendeeChangedDecisionEvent castedEvent) {
			processed = when(castedEvent);
		}
		else if (event instanceof MeetingAttendeeRemovedEvent castedEvent) {
			processed = when(castedEvent);
		}
		
		if (processed)
			aggregateRoot.incrementVersion();
			
		return processed;
	}

	@Override
	public List<Entity> getDirectChildEntities() {
		var entities = new ArrayList<Entity>();
		return entities;
	}

}
