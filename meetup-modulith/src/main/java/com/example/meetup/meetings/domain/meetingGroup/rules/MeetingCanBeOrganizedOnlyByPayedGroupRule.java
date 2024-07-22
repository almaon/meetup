package com.example.meetup.meetings.domain.meetingGroup.rules;

import java.time.LocalDateTime;

import com.example.meetup.meetings.base.SystemClock;
import com.example.meetup.meetings.base.domain.IBusinessRule;

public class MeetingCanBeOrganizedOnlyByPayedGroupRule implements IBusinessRule {

	private LocalDateTime paymentDateTo;

	public MeetingCanBeOrganizedOnlyByPayedGroupRule(LocalDateTime paymentDateTo) {
		this.paymentDateTo = paymentDateTo;
	}

	@Override
	public boolean isBroken() {
		return paymentDateTo == null || paymentDateTo.isBefore(SystemClock.now());
	}

	@Override
	public String message() {
		return "Meeting can be organized only by payed group";
	}
}
