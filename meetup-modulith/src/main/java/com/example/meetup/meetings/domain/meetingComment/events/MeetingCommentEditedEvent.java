package com.example.meetup.meetings.domain.meetingComment.events;


import com.example.meetup.meetings.base.domain.DomainEventBase;

import java.time.LocalDateTime;
import java.util.UUID;
	
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter


@AllArgsConstructor
@NoArgsConstructor
public class MeetingCommentEditedEvent extends DomainEventBase {


	private UUID meetingCommentId;
	private String editedComment;
	private LocalDateTime editDate;
	
}