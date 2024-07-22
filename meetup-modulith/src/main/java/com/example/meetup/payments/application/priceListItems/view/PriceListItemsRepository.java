package com.example.meetup.payments.application.priceListItems.view;

import java.util.List;

import java.util.UUID;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface PriceListItemsRepository extends JpaRepository<PriceListItemsView, UUID> {

	PriceListItemsView findByPriceListItemId(UUID priceListItemId);
	


}
