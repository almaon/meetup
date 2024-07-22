package com.example.meetup.payments.application.priceListItems;

import org.springframework.stereotype.Component;

import com.example.meetup.payments.application.priceListItems.view.PriceListItemsRepository;
import com.example.meetup.payments.domain.MoneyValue;
import com.example.meetup.payments.domain.SubscriptionPeriod;
import com.example.meetup.payments.domain.priceListItems.PriceList;
import com.example.meetup.payments.domain.priceListItems.PriceListItemCategory;
import com.example.meetup.payments.domain.priceListItems.PriceListItemData;
import com.example.meetup.payments.domain.priceListItems.pricingStrategies.DirectValueFromPriceListPricingStrategy;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class PriceListFactory {

	private final PriceListItemsRepository priceListItemsRepository;
	
    public PriceList createPriceList() {
    	
    	var priceListItems = priceListItemsRepository
    		.findAll().stream()
    		.map(pli -> 
    			new PriceListItemData(
    					pli.getCountryCode(), 
    					new SubscriptionPeriod(pli.getSubscriptionPeriodCode()), 
    					new MoneyValue(pli.getPriceValue(), pli.getPriceCurrency()), 
    					PriceListItemCategory.valueOf(pli.getCategoryCode())))
    		.toList();
    	
    	  // This is place for selecting pricing strategy based on provided data and the system state.
        var pricingStrategy = new DirectValueFromPriceListPricingStrategy(priceListItems);

        return new PriceList(priceListItems, pricingStrategy);
    }

}
