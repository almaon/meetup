package com.example.meetup.payments.domain.priceListItems;

import java.util.List;
import com.example.meetup.payments.domain.MoneyValue;
import com.example.meetup.payments.domain.SubscriptionPeriod;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import lombok.AllArgsConstructor;
@AllArgsConstructor


@Setter
@Getter
public class PriceListItemData {

	private String countryCode;
	private SubscriptionPeriod subscriptionPeriod;
	private MoneyValue value;
	private PriceListItemCategory category;

	
	
}
