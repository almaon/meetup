package com.example.meetup.payments.domain.subscriptionRenewalPayments.events;


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
public class SubscriptionRenewalPaymentPaidEvent extends DomainEventBase {


	private UUID subscriptionRenewalPaymentId;
	private UUID subscriptionId;
	private String status;
	
}
