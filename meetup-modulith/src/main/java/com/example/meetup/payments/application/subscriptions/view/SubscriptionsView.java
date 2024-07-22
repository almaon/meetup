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
public class SubscriptionsView {
	
	@Id
	@Column(name = "id", length = 16, unique = true, nullable = false)
	private UUID subscriptionId;

	private String period;
	private String status;
	private LocalDateTime expirationDate;
	
}
