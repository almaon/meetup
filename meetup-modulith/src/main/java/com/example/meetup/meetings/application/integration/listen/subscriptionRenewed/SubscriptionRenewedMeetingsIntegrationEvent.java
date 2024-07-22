package com.example.meetup.meetings.application.integration.listen.subscriptionRenewed;

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
public class SubscriptionRenewedMeetingsIntegrationEvent extends DomainEventBase {


	private UUID subscriptionId;
	private String status;
	private LocalDateTime expirationDate;
	private UUID payerId;
	private String subscriptionPeriodCode;
	
}
