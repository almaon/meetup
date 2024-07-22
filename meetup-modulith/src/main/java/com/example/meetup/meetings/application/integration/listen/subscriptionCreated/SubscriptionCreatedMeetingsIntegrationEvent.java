package com.example.meetup.meetings.application.integration.listen.subscriptionCreated;

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
public class SubscriptionCreatedMeetingsIntegrationEvent extends DomainEventBase {


	private UUID subscriptionId;
	private UUID payerId;
	private UUID subscriptionPaymentId;
	private String subscriptionPeriodCode;
	private String countryCode;
	private String status;
	private LocalDateTime expirationDate;
	
}
