package com.example.meetup.payments.application.priceListItems.view;


import jakarta.persistence.Column;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.UUID;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PriceListItemsView {
	
	@Id
	@Column(name = "id", length = 16, unique = true, nullable = false)
	private UUID priceListItemId;

	private String countryCode;
	private String categoryCode;
	private String subscriptionPeriodCode;
	private String priceCurrency;
	private double priceValue;
	private Boolean isActive;
	
}
