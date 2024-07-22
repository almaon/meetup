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
public class PriceListItemDeactivatedEvent extends DomainEventBase {


	private UUID priceListItemId;
	
}
