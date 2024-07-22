package com.example.meetup.payments.domain.subscriptionPayments;

import com.example.meetup.payments.domain.SubscriptionPeriod;
import com.example.meetup.payments.domain.payers.PayerId;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@AllArgsConstructor


@Setter
@Getter
public class SubscriptionPaymentSnapshot {

	private PayerId payerId;
	private SubscriptionPeriod subscriptionPeriod;
	private String countryCode;
	private SubscriptionPaymentId subscriptionPaymentId;

	
	
}
