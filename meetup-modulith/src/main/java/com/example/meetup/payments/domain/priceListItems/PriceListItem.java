package com.example.meetup.payments.domain.priceListItems;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.example.meetup.payments.base.domain.Aggregate;
import com.example.meetup.payments.base.domain.Entity;
import com.example.meetup.payments.base.domain.IDomainEvent;
import com.example.meetup.payments.domain.MoneyValue;
import com.example.meetup.payments.domain.SubscriptionPeriod;
import com.example.meetup.payments.domain.priceListItems.events.PriceListItemActivatedEvent;
import com.example.meetup.payments.domain.priceListItems.events.PriceListItemAttributesChangedEvent;
import com.example.meetup.payments.domain.priceListItems.events.PriceListItemCreatedEvent;
import com.example.meetup.payments.domain.priceListItems.events.PriceListItemDeactivatedEvent;

import lombok.Getter;
import lombok.Setter;


@Getter
public class PriceListItem extends Aggregate {
	
	// business id
	@Setter // for testing
	protected PriceListItemId priceListItemId;
	
	public String getStreamId() {
		return "Payments-PriceListItem-" + priceListItemId.getValue();
	}
		
	protected String countryCode;
	protected SubscriptionPeriod subscriptionPeriod;
	protected PriceListItemCategory category;
	protected MoneyValue price;
	protected Boolean isActive;
	
	public PriceListItem() {
	}
	
	public PriceListItem(String countryCode, SubscriptionPeriod subscriptionPeriod, PriceListItemCategory category, MoneyValue price) {

		var priceListItemCreated = new PriceListItemCreatedEvent(
			UUID.randomUUID(),
			countryCode,
			price.getValue(),
			category.name(),
			price.getCurrency(),
			subscriptionPeriod.getName(),
			true);
 
		apply(priceListItemCreated);
		addDomainEvent(priceListItemCreated);

	}
	
	public void activate() {

		var priceListItemActivated = new PriceListItemActivatedEvent(
				priceListItemId.getValue());
 
		apply(priceListItemActivated);
		addDomainEvent(priceListItemActivated);

	}	
	public void deactivate() {

		var priceListItemDeactivated = new PriceListItemDeactivatedEvent(
				priceListItemId.getValue());
 
		apply(priceListItemDeactivated);
		addDomainEvent(priceListItemDeactivated);

	}	
	public void changeAttributes(String countryCode, SubscriptionPeriod subscriptionPeriod, PriceListItemCategory category, MoneyValue price) {

		var priceListItemAttributesChanged = new PriceListItemAttributesChangedEvent(
				priceListItemId.getValue(),
				countryCode,
				price.getValue(),
				category.name(),
				price.getCurrency(),
				subscriptionPeriod.getName());
 
		apply(priceListItemAttributesChanged);
		addDomainEvent(priceListItemAttributesChanged);

	}	
	
	
	
	private boolean when(PriceListItemAttributesChangedEvent event) {

		countryCode = event.getCountryCode();
		subscriptionPeriod = new SubscriptionPeriod(event.getSubscriptionPeriodCode());
		category = PriceListItemCategory.valueOf(event.getCategoryCode());
		price = new MoneyValue(event.getPriceValue(), event.getPriceCurrency());
		
		return true;

	}
	
	private boolean when(PriceListItemDeactivatedEvent event) {

		isActive = false;
		
		return true;

	}
	
	private boolean when(PriceListItemActivatedEvent event) {

		isActive = true;
		
		return true;

	}
	
	private boolean when(PriceListItemCreatedEvent event) {

		priceListItemId = new PriceListItemId(event.getPriceListItemId());
		countryCode = event.getCountryCode();
		subscriptionPeriod = new SubscriptionPeriod(event.getSubscriptionPeriodCode());
		category = PriceListItemCategory.valueOf(event.getCategoryCode());
		price = new MoneyValue(event.getPriceValue(), event.getPriceCurrency());
		isActive = event.getIsActive();
		
		return true;

	}
	
	@Override
	protected boolean apply(IDomainEvent event) {
	
		if (event instanceof PriceListItemAttributesChangedEvent castedEvent) {
			return when(castedEvent);
		}
	
		if (event instanceof PriceListItemDeactivatedEvent castedEvent) {
			return when(castedEvent);
		}
	
		if (event instanceof PriceListItemActivatedEvent castedEvent) {
			return when(castedEvent);
		}
	
		if (event instanceof PriceListItemCreatedEvent castedEvent) {
			return when(castedEvent);
		}
		return false;
	}
	
	@Override
	public List<Entity> getDirectChildEntities() {
		var entities = new ArrayList<Entity>();
		return entities;
	}
	
	
}
