package com.example.meetup.payments.domain.subscriptions;


import com.example.meetup.payments.base.domain.Aggregate;
import com.example.meetup.payments.base.domain.IDomainEvent;
import com.example.meetup.payments.base.domain.Entity;

	
import com.example.meetup.payments.domain.subscriptions.events.SubscriptionExpiredEvent;
	
import com.example.meetup.payments.domain.subscriptions.events.SubscriptionRenewedEvent;
	
import com.example.meetup.payments.domain.subscriptions.events.SubscriptionCreatedEvent;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.UUID;
import com.example.meetup.payments.domain.subscriptionPayments.SubscriptionPaymentSnapshot;
import com.example.meetup.payments.domain.subscriptionRenewalPayments.SubscriptionRenewalPaymentSnapshot;
import com.example.meetup.payments.domain.SubscriptionPeriod;
import lombok.Getter;
import lombok.Setter;


@Getter
public class Subscription extends Aggregate {
	
	// business id
	@Setter // for testing
	protected SubscriptionId subscriptionId;
	
	public String getStreamId() {
		return "Payments-Subscription-" + subscriptionId.getValue();
	}
		
	protected SubscriberId subscriberId;
	protected SubscriptionPeriod subscriptionPeriod;
	protected SubscriptionStatus status;
	protected String countryCode;
	protected LocalDateTime expirationDate;
	
	public Subscription() {
	}
	
	public Subscription(SubscriptionPaymentSnapshot subscriptionPayment) {

        var expirationDate = SubscriptionDateExpirationCalculator.calculateForNew(subscriptionPayment.getSubscriptionPeriod());

		var subscriptionCreated = new SubscriptionCreatedEvent(
			UUID.randomUUID(),
			subscriptionPayment.getPayerId().getValue(),
			subscriptionPayment.getSubscriptionPaymentId().getValue(),
			subscriptionPayment.getSubscriptionPeriod().getName(),
			subscriptionPayment.getCountryCode(),
			SubscriptionStatus.Active.name(),
			expirationDate);
 
		apply(subscriptionCreated);
		addDomainEvent(subscriptionCreated);

	}
	
	public void renew(SubscriptionRenewalPaymentSnapshot subscriptionRenewalPayment) {

        var expirationDate = SubscriptionDateExpirationCalculator
        		.calculateForRenewal(this.expirationDate, subscriptionRenewalPayment.getSubscriptionPeriod());

		var subscriptionRenewed = new SubscriptionRenewedEvent(
			subscriberId.getValue(),
			SubscriptionStatus.Active.name(),
			expirationDate,
			subscriptionRenewalPayment.getPayerId().getValue(),
			subscriptionRenewalPayment.getSubscriptionPeriod().getName());
 
		apply(subscriptionRenewed);
		addDomainEvent(subscriptionRenewed);

	}	
	public void expire() {

		var subscriptionExpired = new SubscriptionExpiredEvent(
				subscriptionId.getValue(),
				status.name());
 
		apply(subscriptionExpired);
		addDomainEvent(subscriptionExpired);

	}	
	
	
	
	private boolean when(SubscriptionExpiredEvent event) {

		status = SubscriptionStatus.valueOf(event.getStatus());
		
		return true;

	}
	
	private boolean when(SubscriptionRenewedEvent event) {

		status = SubscriptionStatus.valueOf(event.getStatus());
		subscriptionPeriod = new SubscriptionPeriod(event.getSubscriptionPeriodCode());
		expirationDate = event.getExpirationDate();

		return true;

	}
	
	private boolean when(SubscriptionCreatedEvent event) {

		subscriptionId = new SubscriptionId(event.getSubscriptionId());
		subscriberId = new SubscriberId(event.getPayerId());
		subscriptionPeriod = new SubscriptionPeriod(event.getSubscriptionPeriodCode());
		countryCode = event.getCountryCode();
		status = SubscriptionStatus.valueOf(event.getStatus());
		expirationDate = event.getExpirationDate();
		
		return true;

	}
	
	@Override
	protected boolean apply(IDomainEvent event) {
	
		if (event instanceof SubscriptionExpiredEvent castedEvent) {
			return when(castedEvent);
		}
	
		if (event instanceof SubscriptionRenewedEvent castedEvent) {
			return when(castedEvent);
		}
	
		if (event instanceof SubscriptionCreatedEvent castedEvent) {
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
