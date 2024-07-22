package com.example.meetup.meetings.application.meetingComments.view;


import jakarta.persistence.Column;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
public class MeetingCommentLikersView {
	
	@Id
	@Column(name = "id", length = 16, unique = true, nullable = false)
	private UUID meetingCommentId;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<MeetingCommentLiker> meetingCommentLikers;
	
}
