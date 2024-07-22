package com.example.meetup.meetings.domain.meeting.rules;

import com.example.meetup.meetings.base.SystemClock;
import com.example.meetup.meetings.base.domain.IBusinessRule;
import com.example.meetup.meetings.domain.meeting.Term;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AttendeeCanBeAddedOnlyInRsvpTermRule implements IBusinessRule {

	private final Term rsvpTerm;

	@Override
	public boolean isBroken() {
		return rsvpTerm != null && !rsvpTerm.isInTerm(SystemClock.now());
	}

	@Override
	public String message() {
		return "Attendee can be added only in RSVP term";
	}
}
