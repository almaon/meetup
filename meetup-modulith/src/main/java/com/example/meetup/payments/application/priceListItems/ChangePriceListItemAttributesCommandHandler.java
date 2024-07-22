package com.example.meetup.payments.application.priceListItems;


import org.springframework.stereotype.Component;

import com.example.meetup.payments.base.application.IAsyncCommandHandler;
import com.example.meetup.payments.base.domain.IAggregateStore;
import com.example.meetup.payments.domain.MoneyValue;
import com.example.meetup.payments.domain.SubscriptionPeriod;
import com.example.meetup.payments.domain.priceListItems.PriceListItem;
import com.example.meetup.payments.domain.priceListItems.PriceListItemCategory;
import com.example.meetup.payments.domain.priceListItems.PriceListItemId;

import lombok.RequiredArgsConstructor;

    
@Component
@RequiredArgsConstructor
public class ChangePriceListItemAttributesCommandHandler implements IAsyncCommandHandler<ChangePriceListItemAttributesCommand> {

	private final IAggregateStore aggregateStore;


	@Override
	public void handle(ChangePriceListItemAttributesCommand command) {

		PriceListItem priceListItem = aggregateStore.load(new PriceListItemId(command.getPriceListItemId()), PriceListItem.class);
    	
		priceListItem.changeAttributes(
				command.getCountryCode(), 
				new SubscriptionPeriod(command.getSubscriptionPeriodCode()), 
				PriceListItemCategory.valueOf(command.getCategoryCode()), 
				new MoneyValue(command.getPriceValue(), command.getPriceCurrency()));
		
		aggregateStore.save(priceListItem);

	}	
    	
}
