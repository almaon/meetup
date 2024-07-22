package com.example.meetup.payments.domain.subscriptions.events;


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
public class SubscriptionCreatedEvent extends DomainEventBase {


	private UUID subscriptionId;
	private UUID payerId;
	private UUID subscriptionPaymentId;
	private String subscriptionPeriodCode;
	private String countryCode;
	private String status;
	private LocalDateTime expirationDate;
	
}
