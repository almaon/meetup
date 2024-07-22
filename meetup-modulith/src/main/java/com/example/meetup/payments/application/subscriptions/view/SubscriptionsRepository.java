package com.example.meetup.payments.application.subscriptions.view;

import java.util.List;

import java.util.UUID;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface SubscriptionsRepository extends JpaRepository<SubscriptionsView, UUID> {

	SubscriptionsView findBySubscriptionId(UUID subscriptionId);
	


}
