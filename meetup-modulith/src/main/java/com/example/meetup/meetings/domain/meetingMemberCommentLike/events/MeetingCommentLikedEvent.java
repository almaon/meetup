package com.example.meetup.meetings.domain.meetingMemberCommentLike.events;


import com.example.meetup.meetings.base.domain.DomainEventBase;

import java.util.UUID;
	
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter


@AllArgsConstructor
@NoArgsConstructor
public class MeetingCommentLikedEvent extends DomainEventBase {


	private UUID meetingCommentId;
	private UUID likerId;
	private UUID meetingMemberCommentLikeId;
	
}
