package com.example.meetup.meetings.application.meetings.view;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MeetingFeePaymentStatus {

	@Id
	@Column(name = "id", length = 16, unique = true, nullable = false)
	private UUID attendeeId;
	
	private Boolean isPayed;
	private LocalDateTime payedDate;

}
