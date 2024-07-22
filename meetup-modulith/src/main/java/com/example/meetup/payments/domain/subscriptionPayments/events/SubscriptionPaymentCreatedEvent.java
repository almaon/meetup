package com.example.meetup.payments.domain.subscriptionPayments.events;


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
public class SubscriptionPaymentCreatedEvent extends DomainEventBase {


	private UUID subscriptionPaymentId;
	private String status;
	private UUID payerId;
	private String subscriptionPeriodCode;
	private double value;
	private String countryCode;
	private String currency;
	
}
