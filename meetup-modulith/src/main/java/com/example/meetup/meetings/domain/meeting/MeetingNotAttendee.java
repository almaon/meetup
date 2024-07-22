package com.example.meetup.meetings.domain.meeting;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.meetup.meetings.base.SystemClock;
import com.example.meetup.meetings.base.domain.Aggregate;
import com.example.meetup.meetings.base.domain.Entity;
import com.example.meetup.meetings.base.domain.IDomainEvent;
import com.example.meetup.meetings.domain.meeting.events.MeetingNotAttendeeChangedDecisionEvent;
import com.example.meetup.meetings.domain.member.MemberId;

import lombok.Getter;

@Getter
public class MeetingNotAttendee extends Entity {

	// business id
	protected MemberId memberId;
	protected MeetingId meetingId;
	protected LocalDateTime decisionDate;
	protected boolean decisionChanged;
	protected LocalDateTime decisionChangeDate;

	MeetingNotAttendee(Aggregate root, MeetingId meetingId, MemberId memberId) {
		super(root);
		
		this.meetingId = meetingId;
		this.memberId = memberId;
	}

	void changeDecision() {
		var meetingNotAttendeeChangedDecision = new MeetingNotAttendeeChangedDecisionEvent(
				meetingId.getValue(), 
				memberId.getValue(),
				SystemClock.now());

		apply(meetingNotAttendeeChangedDecision);
		addDomainEvent(meetingNotAttendeeChangedDecision);
	}	

	public boolean isActiveNotAttendee(MemberId memberId) {
		return !decisionChanged && this.memberId.equals(memberId);
	}	


	private boolean when(MeetingNotAttendeeChangedDecisionEvent event) {
		if (new MemberId(event.getMemberId()).equals(memberId)) {
			decisionChanged = true;
			decisionChangeDate = event.getDecisionChangeDate();
			return true;
		}
		return false;
	}

	@Override
	protected boolean apply(IDomainEvent event) {

		var processed = false;
		
		if (event instanceof MeetingNotAttendeeChangedDecisionEvent castedEvent) {
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
