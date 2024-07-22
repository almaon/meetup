package com.example.meetup.payments.application.priceListItems.view;


import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import com.example.meetup.payments.base.application.IProjector;
import com.example.meetup.payments.base.application.IEvent;

import com.example.meetup.payments.domain.priceListItems.events.PriceListItemActivatedEvent;
import com.example.meetup.payments.domain.priceListItems.events.PriceListItemAttributesChangedEvent;
import com.example.meetup.payments.domain.priceListItems.events.PriceListItemCreatedEvent;
import com.example.meetup.payments.domain.priceListItems.events.PriceListItemDeactivatedEvent;

import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class PriceListItemsProjector implements IProjector {

	private final PriceListItemsRepository priceListItemsRepository;
	


	
	private void when(PriceListItemAttributesChangedEvent event) {

		var view = priceListItemsRepository.findByPriceListItemId(event.getPriceListItemId());
		
		view.setCountryCode(event.getCountryCode());
		view.setPriceValue(event.getPriceValue());
		view.setCategoryCode(event.getCategoryCode());
		view.setPriceCurrency(event.getPriceCurrency());
		view.setSubscriptionPeriodCode(event.getSubscriptionPeriodCode());

	}
	private void when(PriceListItemDeactivatedEvent event) {

		var view = priceListItemsRepository.findByPriceListItemId(event.getPriceListItemId());
		
		view.setIsActive(false);

	}
	private void when(PriceListItemActivatedEvent event) {

		var view = priceListItemsRepository.findByPriceListItemId(event.getPriceListItemId());
		
		view.setIsActive(true);

	}
	private void when(PriceListItemCreatedEvent event) {

		priceListItemsRepository.save(
			new PriceListItemsView(
				event.getPriceListItemId(),
				event.getCountryCode(),
				event.getCategoryCode(),
				event.getSubscriptionPeriodCode(),
				event.getPriceCurrency(),
				event.getPriceValue(),
				event.getIsActive()));

	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void project(IEvent event) {
		
		if (event instanceof PriceListItemAttributesChangedEvent castedEvent) {
			when(castedEvent);
			return;
		}
		if (event instanceof PriceListItemDeactivatedEvent castedEvent) {
			when(castedEvent);
			return;
		}
		if (event instanceof PriceListItemActivatedEvent castedEvent) {
			when(castedEvent);
			return;
		}
		if (event instanceof PriceListItemCreatedEvent castedEvent) {
			when(castedEvent);
			return;
		}
			
	}
	
}
