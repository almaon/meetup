package com.example.meetup.payments.domain.meetingFeePayments.events;


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
public class MeetingFeePaymentCreatedEvent extends DomainEventBase {


	private UUID meetingFeeId;
	private UUID meetingFeePaymentId;
	private String status;
	
}