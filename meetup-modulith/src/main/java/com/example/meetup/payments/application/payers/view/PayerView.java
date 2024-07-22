package com.example.meetup.payments.application.payers.view;


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
public class PayerView {
	
	@Id
	@Column(name = "id", length = 16, unique = true, nullable = false)
	private UUID payerId;

	private String login;
	private String firstName;
	private String lastName;
	private String email;
	private LocalDateTime registerDate;
	
}
