package com.example.meetup.payments.application.priceListItems;


import org.springframework.stereotype.Component;

import com.example.meetup.payments.base.application.IAsyncCommandHandler;
import com.example.meetup.payments.base.domain.IAggregateStore;
import com.example.meetup.payments.domain.priceListItems.PriceListItem;
import com.example.meetup.payments.domain.priceListItems.PriceListItemId;

import lombok.RequiredArgsConstructor;

    
@Component
@RequiredArgsConstructor
public class DeactivatePriceListItemCommandHandler implements IAsyncCommandHandler<DeactivatePriceListItemCommand> {

	private final IAggregateStore aggregateStore;


	@Override
	public void handle(DeactivatePriceListItemCommand command) {

		PriceListItem priceListItem = aggregateStore.load(new PriceListItemId(command.getPriceListItemId()), PriceListItem.class);
    	
		priceListItem.deactivate();
		
		aggregateStore.save(priceListItem);

	}	
    	
}
