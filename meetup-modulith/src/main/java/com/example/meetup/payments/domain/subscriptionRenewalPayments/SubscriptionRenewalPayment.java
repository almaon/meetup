package com.example.meetup.payments.domain.subscriptionRenewalPayments;


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
import com.example.meetup.payments.domain.subscriptionRenewalPayments.events.SubscriptionRenewalPaymentCreatedEvent;
import com.example.meetup.payments.domain.subscriptionRenewalPayments.events.SubscriptionRenewalPaymentPaidEvent;
import com.example.meetup.payments.domain.subscriptions.SubscriptionId;

import lombok.Getter;
import lombok.Setter;


@Getter
public class SubscriptionRenewalPayment extends Aggregate {
	
	// business id
	@Setter // for testing
	protected SubscriptionRenewalPaymentId subscriptionRenewalPaymentId;
	
	public String getStreamId() {
		return "Payments-SubscriptionRenewalPayment-" + subscriptionRenewalPaymentId.getValue();
	}
		
	protected PayerId payerId;
	protected SubscriptionId subscriptionId;
	protected SubscriptionPeriod subscriptionPeriod;
	protected String countryCode;
	protected SubscriptionRenewalPaymentStatus status;
	protected MoneyValue value;
	
	public SubscriptionRenewalPayment() {
	}
	
	public SubscriptionRenewalPayment(PayerId payerId, SubscriptionId subscriptionId, SubscriptionPeriod period, String countryCode, MoneyValue priceOffer, PriceList priceList) {

		var subscriptionRenewalPaymentCreated = new SubscriptionRenewalPaymentCreatedEvent(
				UUID.randomUUID(),
				subscriptionId.getValue(),
				SubscriptionRenewalPaymentStatus.WaitingForPayment.name(),
				priceOffer.getValue(),
				countryCode,
				priceOffer.getCurrency(),
				period.getName(),
				payerId.getValue());
 
		apply(subscriptionRenewalPaymentCreated);
		addDomainEvent(subscriptionRenewalPaymentCreated);

	}
	
	public void markRenewalAsPaid() {

		var subscriptionRenewalPaymentPaid = new SubscriptionRenewalPaymentPaidEvent(
				subscriptionRenewalPaymentId.getValue(),
				subscriptionId.getValue(),
				status.name());
 
		apply(subscriptionRenewalPaymentPaid);
		addDomainEvent(subscriptionRenewalPaymentPaid);

	}	
	
	public SubscriptionRenewalPaymentSnapshot getSnapshot() {

        return new SubscriptionRenewalPaymentSnapshot(
        		payerId, 
        		subscriptionPeriod, 
        		countryCode,
        		subscriptionRenewalPaymentId);

	}	
	
	
	private boolean when(SubscriptionRenewalPaymentCreatedEvent event) {

		subscriptionRenewalPaymentId = new SubscriptionRenewalPaymentId(event.getSubscriptionRenewalPaymentId());
		payerId = new PayerId(event.getPayerId());
		subscriptionId = new SubscriptionId(event.getSubscriptionId());
		subscriptionPeriod = new SubscriptionPeriod(event.getSubscriptionPeriodCode());
		countryCode = event.getCountryCode();
		status = SubscriptionRenewalPaymentStatus.valueOf(event.getStatus());
		value = new MoneyValue(event.getValue(), event.getCurrency());
		
		return true;

	}
	
	private boolean when(SubscriptionRenewalPaymentPaidEvent event) {

		status = SubscriptionRenewalPaymentStatus.valueOf(event.getStatus());

		return true;

	}
	
	@Override
	protected boolean apply(IDomainEvent event) {
	
		if (event instanceof SubscriptionRenewalPaymentCreatedEvent castedEvent) {
			return when(castedEvent);
		}
	
		if (event instanceof SubscriptionRenewalPaymentPaidEvent castedEvent) {
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
