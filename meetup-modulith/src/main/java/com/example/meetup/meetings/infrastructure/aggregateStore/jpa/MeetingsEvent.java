package com.example.meetup.meetings.infrastructure.aggregateStore.jpa;

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
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MeetingsEvent {
	
	@Id
	@Column(name = "id", length = 16, unique = true, nullable = false)
	private UUID id;

	private String eventName;
	
	private LocalDateTime occuredOn;
	
	private String data;
	
}
