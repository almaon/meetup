package com.example.meetup.meetings.application.meetings.view;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Meeting {


	@Id
	@Column(name = "id", length = 16, unique = true, nullable = false)
	private UUID meetingId;

	
	private LocalDateTime termEndDate;
	private String title;
	private String meetingLocationPostalCode;
	private LocalDateTime termStartDate;
	private String meetingLocationAddress;
	private String description;
	private String meetingLocationCity;
	private int guestsNumber;
	private Boolean isCanceled;

}
