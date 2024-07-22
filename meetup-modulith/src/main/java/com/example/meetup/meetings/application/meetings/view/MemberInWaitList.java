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
public class MemberInWaitList {


	@Id
	@Column(name = "id", length = 16, unique = true, nullable = false)

	
	private UUID memberId;
	private String name;
	private int position;
	private LocalDateTime signUpDate;
	private LocalDateTime signOffDate;
	private LocalDateTime movedToAttendeesDate;
	private Boolean isSignedOff;
	private Boolean isMovedToAttendee;

}
