package com.example.meetup.meetings.application.meetings.view;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MeetingDetailsView {
	
	@Id
	@Column(name = "id", length = 16, unique = true, nullable = false)
	private UUID meetingId;

	private String title;
	private String description;
	private String meetingLocationCity;
	private String meetingLocationPostalCode;
	private String meetingLocationAddress;
	private LocalDateTime termStartDate;
	private LocalDateTime termEndDate;
	private int attendeesCount;
	private int guestsCount;
	private LocalDateTime rsvpTermEndDate;
	private LocalDateTime rsvpTermStartDate;
	private int guestsLimit;
	private String meetingLocationName;
	private String eventFeeCurrency;
	private double eventFeeValue;
	private UUID meetingGroupId;
	private String meetingGroupName;
	
	@ElementCollection
	private List<UUID> hosts;
	private Boolean isCanceled;
	private int attendeesLimit;
	
}
