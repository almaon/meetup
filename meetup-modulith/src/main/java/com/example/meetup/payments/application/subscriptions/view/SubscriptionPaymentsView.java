package com.example.meetup.payments.application.subscriptions.view;


import jakarta.persistence.Column;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SubscriptionPaymentsView {
	
	@Id
	@Column(name = "id", length = 16, unique = true, nullable = false)
	private UUID paymentId;

	private UUID payerId;
	private String type;
	private String status;
	private LocalDateTime date;
	private UUID subscriptionId;
	private double moneyValue;
	private String moneyCurrency;
	private String period;
	
}
