package com.example.meetup.payments.application.subscriptions.view;

import java.util.List;

import java.util.UUID;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface SubscriptionPaymentsRepository extends JpaRepository<SubscriptionPaymentsView, UUID> {

	SubscriptionPaymentsView findByPaymentId(UUID paymentId);
	


}
