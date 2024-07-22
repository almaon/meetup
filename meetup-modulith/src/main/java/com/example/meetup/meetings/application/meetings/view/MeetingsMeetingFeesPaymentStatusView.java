package com.example.meetup.meetings.application.meetings.view;


import jakarta.persistence.Column;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.List;
import java.util.UUID;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MeetingsMeetingFeesPaymentStatusView {
	
	@Id
	@Column(name = "id", length = 16, unique = true, nullable = false)
	private UUID meetingId;

	@OneToMany(cascade = CascadeType.ALL)
	private List<MeetingFeePaymentStatus> meetingFeePaymentStatus;
	
}
