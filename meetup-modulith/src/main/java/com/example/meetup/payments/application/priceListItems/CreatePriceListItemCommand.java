package com.example.meetup.payments.application.priceListItems;


import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.NoArgsConstructor;

import com.example.meetup.payments.base.application.CommandBase;
import com.example.meetup.payments.base.application.ICommandHandler;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreatePriceListItemCommand extends CommandBase {

	private String countryCode;
	private String categoryCode;
	private double priceValue;
	private String priceCurrency;
	private String subscriptionPeriodCode;
    	
    @Override
    public Class<? extends ICommandHandler> getHandlerType() {
        return CreatePriceListItemCommandHandler.class;
    }

}
