package com.example.meetup.meetings.domain.meeting;


import com.example.meetup.meetings.base.domain.ValueObject;
import com.example.meetup.meetings.domain.meeting.rules.MeetingAttendeesLimitCannotBeNegativeRule;
import com.example.meetup.meetings.domain.meeting.rules.MeetingAttendeesLimitMustBeGreaterThanGuestsLimitRule;
import com.example.meetup.meetings.domain.meeting.rules.MeetingGuestsLimitCannotBeNegativeRule;

import lombok.EqualsAndHashCode;
import lombok.Getter;


@EqualsAndHashCode(callSuper = false)
@Getter
public class MeetingLimits extends ValueObject {

	private Integer attendeesLimit;
	private Integer guestsLimit;
	public MeetingLimits(Integer attendeesLimit, Integer guestsLimit) {
		
		checkRule(new MeetingAttendeesLimitCannotBeNegativeRule(attendeesLimit));
		checkRule(new MeetingGuestsLimitCannotBeNegativeRule(guestsLimit));
		checkRule(new MeetingAttendeesLimitMustBeGreaterThanGuestsLimitRule(attendeesLimit, guestsLimit));
		
		this.attendeesLimit = attendeesLimit;
		this.guestsLimit = guestsLimit;
	}
	
	
}
