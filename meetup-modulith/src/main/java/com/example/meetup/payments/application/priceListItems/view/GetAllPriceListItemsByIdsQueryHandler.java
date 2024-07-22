package com.example.meetup.payments.application.priceListItems.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.example.meetup.payments.base.application.IQueryHandler;

import java.util.List;


@Component
@RequiredArgsConstructor
public class GetAllPriceListItemsByIdsQueryHandler implements IQueryHandler<GetAllPriceListItemsByIdsQuery, List<PriceListItemsView>> {

	private final PriceListItemsRepository priceListItemsRepository;
	
 	@Override
    public List<PriceListItemsView> handle(GetAllPriceListItemsByIdsQuery query) {
    	return priceListItemsRepository.findAllById(query.getIds());
    }

}
