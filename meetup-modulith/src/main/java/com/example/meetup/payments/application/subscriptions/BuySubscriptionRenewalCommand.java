package com.example.meetup.payments.application.subscriptions;


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
public class BuySubscriptionRenewalCommand extends CommandBase {

	private UUID subscriptionId;
	private String currency;
	private double value;
	private String subscriptionTypeCode;
	private String countryCode;
    	
    @Override
    public Class<? extends ICommandHandler> getHandlerType() {
        return BuySubscriptionRenewalCommandHandler.class;
    }

}
