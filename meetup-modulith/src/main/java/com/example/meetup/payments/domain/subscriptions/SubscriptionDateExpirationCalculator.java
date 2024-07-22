package com.example.meetup.payments.domain.subscriptions;

import java.time.LocalDateTime;

import com.example.meetup.payments.base.SystemClock;
import com.example.meetup.payments.domain.SubscriptionPeriod;

public class SubscriptionDateExpirationCalculator {

	public static LocalDateTime calculateForNew(SubscriptionPeriod period) {
		return SystemClock.now().plusMonths(period.getMonthsNumber());
	}

	public static LocalDateTime calculateForRenewal(LocalDateTime expirationDate, SubscriptionPeriod period) {
		if (SystemClock.now().isBefore(expirationDate)) {
			return expirationDate.plusMonths(period.getMonthsNumber());
		}

		return SystemClock.now().plusMonths(period.getMonthsNumber());
	}
}
