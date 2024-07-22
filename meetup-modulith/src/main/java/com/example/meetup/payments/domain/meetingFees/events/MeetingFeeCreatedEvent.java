package com.example.meetup.payments.domain.meetingFees.events;


import com.example.meetup.payments.base.domain.DomainEventBase;

import java.util.UUID;
	
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter


@AllArgsConstructor
@NoArgsConstructor
public class MeetingFeeCreatedEvent extends DomainEventBase {


	private UUID meetingFeeId;
	private String status;
	private UUID payerId;
	private UUID meetingId;
	private double feeValue;
	private String feeCurrency;
	
}
