package com.example.meetup.payments.application.integration.listen.meetingAttendeeAdded;

import com.example.meetup.payments.base.domain.DomainEventBase;


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
public class MeetingAttendeeAddedPaymentsIntegrationEvent extends DomainEventBase {


	private UUID meetingId;
	private UUID attendeeId;
	private LocalDateTime rsvpDate;
	private String role;
	private int guestsNumber;
	private double feeValue;
	private String feeCurrency;
	
}
