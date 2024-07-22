package com.example.meetup.meetings.application.meetingComments.view;


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
public class MeetingCommentsView {
	
	@Id
	@Column(name = "id", length = 16, unique = true, nullable = false)
	private UUID meetingCommentId;

	private UUID inReplyToCommentId;
	private UUID authorId;
	private String comment;
	private LocalDateTime createDate;
	private LocalDateTime editDate;
	private int likesCount;
	
}
