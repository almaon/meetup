package com.example.meetup.meetings.domain.meeting.events;


import com.example.meetup.meetings.base.domain.DomainEventBase;

import java.time.LocalDateTime;
import java.util.UUID;
	
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter


@AllArgsConstructor
@NoArgsConstructor
public class MeetingAttendeeAddedEvent extends DomainEventBase {


	private UUID meetingId;
	private UUID attendeeId;
	private LocalDateTime rsvpDate;
	private String role;
	private int guestsNumber;
	private double feeValue;
	private String feeCurrency;
	
}