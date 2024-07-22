package com.example.meetup.payments.application.subscriptions;


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
public class BuySubscriptionCommand extends CommandBase {

	private String subscriptionTypeCode;
	private String countryCode;
	private double value;
	private String currency;
    	
    @Override
    public Class<? extends ICommandHandler> getHandlerType() {
        return BuySubscriptionCommandHandler.class;
    }

}
