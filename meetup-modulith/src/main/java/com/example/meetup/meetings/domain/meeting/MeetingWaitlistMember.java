package com.example.meetup.meetings.domain.meeting;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.meetup.meetings.base.SystemClock;
import com.example.meetup.meetings.base.domain.Aggregate;
import com.example.meetup.meetings.base.domain.Entity;
import com.example.meetup.meetings.base.domain.IDomainEvent;
import com.example.meetup.meetings.domain.meeting.events.MemberSignedOffFromMeetingWaitlistEvent;
import com.example.meetup.meetings.domain.meeting.events.WaitinglistMemberMovedToAttendeesEvent;
import com.example.meetup.meetings.domain.member.MemberId;

import lombok.Getter;

@Getter
public class MeetingWaitlistMember extends Entity {

	// business id
	protected MemberId memberId;
	protected MeetingId meetingId;
	protected LocalDateTime signUpDate;
	protected Boolean isSignedOff;
	protected LocalDateTime signOffDate;
	protected Boolean isMovedToAttendees;
	protected LocalDateTime movedToAttendeesDate;

	MeetingWaitlistMember(Aggregate root, MeetingId meetingId, MemberId memberId, LocalDateTime signUpDate) {
		super(root);

		this.meetingId = meetingId;
		this.memberId = memberId;
		this.signUpDate = signUpDate;
		isMovedToAttendees = false;
	}

	void signOff() {
		var memberSignedOffFromMeetingWaitlist = new MemberSignedOffFromMeetingWaitlistEvent(
				meetingId.getValue(), 
				memberId.getValue(),
				SystemClock.now());

		apply(memberSignedOffFromMeetingWaitlist);
		addDomainEvent(memberSignedOffFromMeetingWaitlist);
	}

	void markIsMovedToAttendees(LocalDateTime movedToAttendeesDate) {
		var waitinglistMemberMovedToAttendees = new WaitinglistMemberMovedToAttendeesEvent(
				memberId.getValue(), 
				movedToAttendeesDate, 
				meetingId.getValue());

		apply(waitinglistMemberMovedToAttendees);
		addDomainEvent(waitinglistMemberMovedToAttendees);
	}	

	boolean isActive() {
		return !isSignedOff && !isMovedToAttendees;
	}

	public boolean isActiveOnWaitList(MemberId MemberId) {
		return this.memberId.equals(memberId) && isActive();
	}

	private boolean when(MemberSignedOffFromMeetingWaitlistEvent event) {
		if (new MemberId(event.getMemberId()).equals(memberId)) {
			isSignedOff = true;
			signOffDate = event.getSignOffDate();
			return true;
		}
		return false;
	}

	private boolean when(WaitinglistMemberMovedToAttendeesEvent event) {
		if (new MemberId(event.getMemberId()).equals(memberId)) {
			movedToAttendeesDate = event.getMovedToAttendeesDate();

			return true;
		}
		return false;
	}

	@Override
	protected boolean apply(IDomainEvent event) {

		if (event instanceof MemberSignedOffFromMeetingWaitlistEvent castedEvent) {
			return when(castedEvent);
		}

		if (event instanceof WaitinglistMemberMovedToAttendeesEvent castedEvent) {
			return when(castedEvent);
		}
		return false;
	}

	@Override
	public List<Entity> getDirectChildEntities() {
		var entities = new ArrayList<Entity>();
		return entities;
	}

}
