package com.example.meetup.payments.application.subscriptions;


import java.util.UUID;

import org.springframework.stereotype.Component;

import com.example.meetup.payments.application.priceListItems.PriceListFactory;
import com.example.meetup.payments.base.application.ISyncCommandHandler;
import com.example.meetup.payments.base.domain.IAggregateStore;
import com.example.meetup.payments.domain.MoneyValue;
import com.example.meetup.payments.domain.SubscriptionPeriod;
import com.example.meetup.payments.domain.subscriptionRenewalPayments.SubscriptionRenewalPayment;
import com.example.meetup.payments.domain.subscriptions.Subscription;
import com.example.meetup.payments.domain.subscriptions.SubscriptionId;
import com.example.meetup.payments.infrastructure.PaymentContext;

import lombok.RequiredArgsConstructor;

    
@Component
@RequiredArgsConstructor
public class BuySubscriptionRenewalCommandHandler implements ISyncCommandHandler<BuySubscriptionRenewalCommand, UUID> {
	
	private final IAggregateStore aggregateStore;

	private final PaymentContext context;
	private final PriceListFactory priceListFactory;

	
	@Override
	public UUID handle(BuySubscriptionRenewalCommand command) {	

		var priceList = priceListFactory.createPriceList();

		Subscription subscription = aggregateStore.load(new SubscriptionId(command.getSubscriptionId()), Subscription.class);
    	
		// TODO: throw exception if subscription does not exists
		
		var subscriptionRenewalPayment = new SubscriptionRenewalPayment(
				context.principalId(),
				new SubscriptionId(command.getSubscriptionId()),
				new SubscriptionPeriod(command.getSubscriptionTypeCode()),
                command.getCountryCode(),
                new MoneyValue(command.getValue(), command.getCurrency()),
                priceList);
		
		aggregateStore.save(subscriptionRenewalPayment);
		return subscriptionRenewalPayment.getSubscriptionRenewalPaymentId().getValue();

	}	
    	
}
