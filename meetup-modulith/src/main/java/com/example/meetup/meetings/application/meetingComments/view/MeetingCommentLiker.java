package com.example.meetup.meetings.application.meetingComments.view;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MeetingCommentLiker {


	@Id
	@Column(name = "id", length = 16, unique = true, nullable = false)

	
	private UUID likerId;
	private String name;
	private UUID meetingMemberCommentLikeId;

}
