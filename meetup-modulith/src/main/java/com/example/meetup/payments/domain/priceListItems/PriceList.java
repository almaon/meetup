package com.example.meetup.payments.domain.priceListItems;

import java.util.List;

import com.example.meetup.payments.base.domain.ValueObject;
import com.example.meetup.payments.domain.MoneyValue;
import com.example.meetup.payments.domain.SubscriptionPeriod;
import com.example.meetup.payments.domain.priceListItems.pricingStrategies.IPricingStrategy;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = false)
@Getter
public class PriceList extends ValueObject {

	private List<PriceListItemData> items;
	private IPricingStrategy pricingStrategy;
	
	public PriceList(List<PriceListItemData> items, IPricingStrategy pricingStrategy) {
		this.items = items;
		this.pricingStrategy = pricingStrategy;
	}
	
	public MoneyValue getPrice(String countryCode, SubscriptionPeriod subscriptionPeriod, PriceListItemCategory category) {
        return pricingStrategy.getPrice(countryCode, subscriptionPeriod, category);
	}	

}
