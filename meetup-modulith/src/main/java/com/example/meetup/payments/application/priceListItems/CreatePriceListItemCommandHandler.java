package com.example.meetup.payments.application.priceListItems;


import java.util.UUID;

import org.springframework.stereotype.Component;

import com.example.meetup.payments.base.application.ISyncCommandHandler;
import com.example.meetup.payments.base.domain.IAggregateStore;
import com.example.meetup.payments.domain.MoneyValue;
import com.example.meetup.payments.domain.SubscriptionPeriod;
import com.example.meetup.payments.domain.priceListItems.PriceListItem;
import com.example.meetup.payments.domain.priceListItems.PriceListItemCategory;

import lombok.RequiredArgsConstructor;

    
@Component
@RequiredArgsConstructor
public class CreatePriceListItemCommandHandler implements ISyncCommandHandler<CreatePriceListItemCommand, UUID> {
	
	private final IAggregateStore aggregateStore;


	
	@Override
	public UUID handle(CreatePriceListItemCommand command) {	

   		var priceListItem = new PriceListItem(
   				command.getCountryCode(), 
   				new SubscriptionPeriod(command.getSubscriptionPeriodCode()), 
   				PriceListItemCategory.valueOf(command.getCategoryCode()), 
   				new MoneyValue(command.getPriceValue(), command.getPriceCurrency()));
    	
		
		aggregateStore.save(priceListItem);
		return priceListItem.getPriceListItemId().getValue();

	}	
    	
}
