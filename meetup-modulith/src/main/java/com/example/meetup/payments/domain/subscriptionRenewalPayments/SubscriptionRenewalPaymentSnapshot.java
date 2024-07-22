package com.example.meetup.payments.domain.subscriptionRenewalPayments;

import java.util.List;
import com.example.meetup.payments.domain.payers.PayerId;
import com.example.meetup.payments.domain.SubscriptionPeriod;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import lombok.AllArgsConstructor;
@AllArgsConstructor


@Setter
@Getter
public class SubscriptionRenewalPaymentSnapshot {

	private PayerId payerId;
	private SubscriptionPeriod subscriptionPeriod;
	private String countryCode;
	private SubscriptionRenewalPaymentId subscriptionRenewalPaymentId;

	
	
}
