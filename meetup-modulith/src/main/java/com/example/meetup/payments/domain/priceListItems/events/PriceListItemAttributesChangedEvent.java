package com.example.meetup.payments.domain.priceListItems.events;


import com.example.meetup.payments.base.domain.DomainEventBase;

import java.util.UUID;
	
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter


@AllArgsConstructor
@NoArgsConstructor
public class PriceListItemAttributesChangedEvent extends DomainEventBase {


	private UUID priceListItemId;
	private String countryCode;
	private double priceValue;
	private String categoryCode;
	private String priceCurrency;
	private String subscriptionPeriodCode;
	
}
