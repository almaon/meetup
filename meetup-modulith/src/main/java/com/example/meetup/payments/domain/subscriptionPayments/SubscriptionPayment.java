package com.example.meetup.payments.domain.subscriptionPayments;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.example.meetup.payments.base.domain.Aggregate;
import com.example.meetup.payments.base.domain.Entity;
import com.example.meetup.payments.base.domain.IDomainEvent;
import com.example.meetup.payments.domain.MoneyValue;
import com.example.meetup.payments.domain.SubscriptionPeriod;
import com.example.meetup.payments.domain.payers.PayerId;
import com.example.meetup.payments.domain.priceListItems.PriceList;
import com.example.meetup.payments.domain.priceListItems.PriceListItemCategory;
import com.example.meetup.payments.domain.subscriptionPayments.events.SubscriptionPaymentCreatedEvent;
import com.example.meetup.payments.domain.subscriptionPayments.events.SubscriptionPaymentExpiredEvent;
import com.example.meetup.payments.domain.subscriptionPayments.events.SubscriptionPaymentPaidEvent;

import lombok.Getter;
import lombok.Setter;


@Getter
public class SubscriptionPayment extends Aggregate {
	
	// business id
	@Setter // for testing
	protected SubscriptionPaymentId subscriptionPaymentId;
	
	public String getStreamId() {
		return "Payments-SubscriptionPayment-" + subscriptionPaymentId.getValue();
	}
		
	protected PayerId payerId;
	protected SubscriptionPeriod subscriptionPeriod;
	protected String countryCode;
	protected SubscriptionPaymentStatus status;
	protected MoneyValue value;
	
	public SubscriptionPayment() {
	}
	
	public SubscriptionPayment(PayerId payerId, SubscriptionPeriod period, String countryCode, MoneyValue priceOffer, PriceList priceList) {

        var priceInPriceList = priceList.getPrice(countryCode, period, PriceListItemCategory.New);

		
		var subscriptionPaymentCreated = new SubscriptionPaymentCreatedEvent(
			UUID.randomUUID(),
			SubscriptionPaymentStatus.WaitingForPayment.name(),
			payerId.getValue(),
			period.getName(),
			priceOffer.getValue(),
			countryCode,
			priceOffer.getCurrency());
 
		apply(subscriptionPaymentCreated);
		addDomainEvent(subscriptionPaymentCreated);

	}
	
	public void markAsPaid() {

		var subscriptionPaymentPaid = new SubscriptionPaymentPaidEvent(
			subscriptionPaymentId.getValue(),
			status.name());
 
		apply(subscriptionPaymentPaid);
		addDomainEvent(subscriptionPaymentPaid);

	}	
	public void expire() {

		var subscriptionPaymentExpired = new SubscriptionPaymentExpiredEvent(
			subscriptionPaymentId.getValue(),
			status.name());
 
		apply(subscriptionPaymentExpired);
		addDomainEvent(subscriptionPaymentExpired);

	}	
	
	public SubscriptionPaymentSnapshot getSnapshot() {

        return new SubscriptionPaymentSnapshot(
        		payerId, 
        		subscriptionPeriod, 
        		countryCode,
        		subscriptionPaymentId);


	}	
	
	
	private boolean when(SubscriptionPaymentCreatedEvent event) {

		subscriptionPaymentId = new SubscriptionPaymentId(event.getSubscriptionPaymentId());
		payerId = new PayerId(event.getPayerId());
		subscriptionPeriod = new SubscriptionPeriod(event.getSubscriptionPeriodCode());
		countryCode = event.getCountryCode();
		status = SubscriptionPaymentStatus.valueOf(event.getStatus());
		value = new MoneyValue(event.getValue(), event.getCurrency());
		
		return true;

	}
	
	private boolean when(SubscriptionPaymentPaidEvent event) {

		status = SubscriptionPaymentStatus.valueOf(event.getStatus());
		
		return true;

	}
	
	private boolean when(SubscriptionPaymentExpiredEvent event) {

		status = SubscriptionPaymentStatus.valueOf(event.getStatus());

		return true;

	}
	
	@Override
	protected boolean apply(IDomainEvent event) {
	
		if (event instanceof SubscriptionPaymentCreatedEvent castedEvent) {
			return when(castedEvent);
		}
	
		if (event instanceof SubscriptionPaymentPaidEvent castedEvent) {
			return when(castedEvent);
		}
	
		if (event instanceof SubscriptionPaymentExpiredEvent castedEvent) {
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
