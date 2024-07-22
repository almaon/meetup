package com.example.meetup.meetings.domain.meeting;

import java.time.LocalDateTime;

import com.example.meetup.meetings.base.SystemClock;
import com.example.meetup.meetings.base.domain.ValueObject;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = false)
@Getter
@AllArgsConstructor
public class MeetingTerm extends ValueObject {

	private LocalDateTime startDate;
	private LocalDateTime endDate;

	public boolean isAfterStart() {
		return SystemClock.now().isAfter(startDate);
	}

}
