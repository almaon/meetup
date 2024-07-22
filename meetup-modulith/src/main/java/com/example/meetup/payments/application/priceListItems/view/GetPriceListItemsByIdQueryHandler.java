package com.example.meetup.payments.application.priceListItems.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.example.meetup.payments.base.application.IQueryHandler;


@Component
@RequiredArgsConstructor
public class GetPriceListItemsByIdQueryHandler implements IQueryHandler<GetPriceListItemsByIdQuery, PriceListItemsView> {

	private final PriceListItemsRepository priceListItemsRepository;
	
 	@Override
    public PriceListItemsView handle(GetPriceListItemsByIdQuery query) {
    	return priceListItemsRepository.findByPriceListItemId(query.getPriceListItemId());
    }

}
