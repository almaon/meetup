package com.example.meetup.payments.application.priceListItems;


import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.NoArgsConstructor;

import com.example.meetup.payments.base.application.CommandBase;
import com.example.meetup.payments.base.application.ICommandHandler;

import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChangePriceListItemAttributesCommand extends CommandBase {

	private UUID priceListItemId;
	private String countryCode;
	private String subscriptionPeriodCode;
	private String categoryCode;
	private double priceValue;
	private String priceCurrency;
    	
    @Override
    public Class<? extends ICommandHandler> getHandlerType() {
        return ChangePriceListItemAttributesCommandHandler.class;
    }

}
