package com.example.meetup.payments.application.priceListItems.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.example.meetup.payments.base.application.IQueryHandler;

import java.util.List;


@Component
@RequiredArgsConstructor
public class GetAllPriceListItemsQueryHandler implements IQueryHandler<GetAllPriceListItemsQuery, List<PriceListItemsView>> {

	private final PriceListItemsRepository priceListItemsRepository;
	
 	@Override
    public List<PriceListItemsView> handle(GetAllPriceListItemsQuery query) {
    	return priceListItemsRepository.findAll();
    }

}
