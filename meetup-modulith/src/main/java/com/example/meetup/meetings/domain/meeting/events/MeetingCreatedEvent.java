package com.example.meetup.meetings.domain.meeting.events;


import com.example.meetup.meetings.base.domain.DomainEventBase;

import java.util.List;
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
public class MeetingCreatedEvent extends DomainEventBase {


	private UUID meetingId;
	private String title;
	private String description;
	private LocalDateTime termStartDate;
	private LocalDateTime termEndDate;
	private String meetingLocationAddress;
	private String meetingLocationName;
	private String meetingLocationCity;
	private String meetingLocationPostalCode;
	private LocalDateTime rsvpTermStartDate;
	private LocalDateTime rsvpTermEndDate;
	private int guestsLimit;
	private double eventFeeValue;
	private String eventFeeCurrency;
	private List<UUID> hostMemberIds;
	private UUID meetingGroupId;
	private LocalDateTime createDate;
	private int attendeesLimit;
	
}
