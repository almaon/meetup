package com.example.meetup.payments.application.subscriptions;


import java.util.UUID;

import org.springframework.stereotype.Component;

import com.example.meetup.payments.application.priceListItems.PriceListFactory;
import com.example.meetup.payments.base.application.ISyncCommandHandler;
import com.example.meetup.payments.base.domain.IAggregateStore;
import com.example.meetup.payments.domain.MoneyValue;
import com.example.meetup.payments.domain.SubscriptionPeriod;
import com.example.meetup.payments.domain.subscriptionPayments.SubscriptionPayment;
import com.example.meetup.payments.infrastructure.PaymentContext;

import lombok.RequiredArgsConstructor;

    
@Component
@RequiredArgsConstructor
public class BuySubscriptionCommandHandler implements ISyncCommandHandler<BuySubscriptionCommand, UUID> {
	
	private final IAggregateStore aggregateStore;

	private final PaymentContext context;
	private final PriceListFactory priceListFactory;

	
	@Override
	public UUID handle(BuySubscriptionCommand command) {	

		var priceList = priceListFactory.createPriceList();
		
   		var subscriptionPayment = new SubscriptionPayment(
   				context.principalId(), 
   				new SubscriptionPeriod(command.getSubscriptionTypeCode()), 
   				command.getCountryCode(), 
   				new MoneyValue(command.getValue(), command.getCurrency()), 
   				priceList);
    	
		aggregateStore.save(subscriptionPayment);
		
		return subscriptionPayment.getSubscriptionPaymentId().getValue();

	}	
    	
}
