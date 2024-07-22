package com.example.meetup.payments.domain.priceListItems.pricingStrategies;

import java.util.List;

import com.example.meetup.payments.domain.MoneyValue;
import com.example.meetup.payments.domain.SubscriptionPeriod;
import com.example.meetup.payments.domain.priceListItems.PriceListItemCategory;
import com.example.meetup.payments.domain.priceListItems.PriceListItemData;

public class DirectValueFromPriceListPricingStrategy implements IPricingStrategy {

	private List<PriceListItemData> items;

	public DirectValueFromPriceListPricingStrategy(List<PriceListItemData> items) {
		this.items = items;
	}

	@Override
	public MoneyValue getPrice(String countryCode, SubscriptionPeriod subscriptionPeriod,
			PriceListItemCategory category) {
		
		var priceListItem = items.stream()
				.filter(x -> x.getCountryCode().equals(countryCode)
						&& x.getSubscriptionPeriod().equals(subscriptionPeriod) && x.getCategory().equals(category))
				.findFirst().get();

		return priceListItem.getValue();
	}

}
