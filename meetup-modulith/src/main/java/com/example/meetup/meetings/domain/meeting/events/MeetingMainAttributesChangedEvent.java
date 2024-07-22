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
public class MeetingMainAttributesChangedEvent extends DomainEventBase {


	private UUID meetingId;
	private String title;
	private String meetingLocationPostalCode;
	private double eventFeeValue;
	private String eventFeeCurrency;
	private LocalDateTime rsvpTermEndDate;
	private String meetingLocationName;
	private LocalDateTime termStartDate;
	private String description;
	private String meetingLocationAddress;
	private String meetingLocationCity;
	private LocalDateTime termEndDate;
	private LocalDateTime rsvpTermStartDate;
	private int guestsLimit;
	private int attendeesLimit;
	
}
