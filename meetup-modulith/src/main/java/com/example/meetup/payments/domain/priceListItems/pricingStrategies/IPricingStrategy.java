package com.example.meetup.payments.domain.priceListItems.pricingStrategies;

import com.example.meetup.payments.domain.MoneyValue;
import com.example.meetup.payments.domain.SubscriptionPeriod;

import com.example.meetup.payments.domain.priceListItems.PriceListItemCategory;


public interface IPricingStrategy {

	 MoneyValue getPrice(String countryCode, SubscriptionPeriod subscriptionPeriod, PriceListItemCategory category);
	
}
