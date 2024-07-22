package com.example.meetup.payments.application.meetingFees.view;


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
public class MeetingFeesView {
	
	@Id
	@Column(name = "id", length = 16, unique = true, nullable = false)
	private UUID meetingFeeId;

	private UUID payerId;
	private UUID meetingId;
	private double feeValue;
	private String feeCurrency;
	private String status;
	
}
