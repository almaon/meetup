package com.example.meetup.meetings.domain.meetingMemberCommentLike;


import com.example.meetup.meetings.base.domain.Aggregate;
import com.example.meetup.meetings.base.domain.IDomainEvent;
import com.example.meetup.meetings.base.domain.Entity;

	
import com.example.meetup.meetings.domain.meetingMemberCommentLike.events.MeetingCommentLikedEvent;
	
import com.example.meetup.meetings.domain.meetingMemberCommentLike.events.MeetingCommentUnlikedEvent;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;
import com.example.meetup.meetings.domain.meetingComment.MeetingCommentId;
import com.example.meetup.meetings.domain.member.MemberId;
import lombok.Getter;
import lombok.Setter;


@Getter
public class MeetingMemberCommentLike extends Aggregate {
	
	// business id
	@Setter // for testing
	protected MeetingMemberCommentLikeId meetingMemberCommentLikeId;
	
	public String getStreamId() {
		return "Meetings-MeetingMemberCommentLike-" + meetingMemberCommentLikeId.getValue();
	}
		
	protected MeetingCommentId meetingCommentId;
	protected MemberId memberId;
	protected Boolean isRemoved;
	
	public MeetingMemberCommentLike() {
	}
	
	public MeetingMemberCommentLike(MeetingCommentId meetingCommentId, MemberId memberId) {

		var meetingCommentLiked = new MeetingCommentLikedEvent(
			meetingCommentId.getValue(),
			memberId.getValue(),
			UUID.randomUUID());
 
		apply(meetingCommentLiked);
		addDomainEvent(meetingCommentLiked);

	}
	
	public void remove() {

		var meetingCommentUnliked = new MeetingCommentUnlikedEvent(
			meetingCommentId.getValue(),
			memberId.getValue(),
			meetingMemberCommentLikeId.getValue());
 
		apply(meetingCommentUnliked);
		addDomainEvent(meetingCommentUnliked);

	}	
	
	
	
	private boolean when(MeetingCommentLikedEvent event) {

		meetingMemberCommentLikeId = new MeetingMemberCommentLikeId(event.getMeetingMemberCommentLikeId());
		meetingCommentId = new MeetingCommentId(event.getMeetingCommentId());
		memberId = new MemberId(event.getLikerId());
		
		return true;

	}
	
	private boolean when(MeetingCommentUnlikedEvent event) {

		isRemoved = true;
		
		return true;

	}
	
	@Override
	protected boolean apply(IDomainEvent event) {
	
		var processed = false;

		if (event instanceof MeetingCommentLikedEvent castedEvent) {
			processed = when(castedEvent);
		}
		else if (event instanceof MeetingCommentUnlikedEvent castedEvent) {
			processed = when(castedEvent);
		}
		
		if (processed)
			incrementVersion();
			
		return processed;	}
	
	@Override
	public List<Entity> getDirectChildEntities() {
		var entities = new ArrayList<Entity>();
		return entities;
	}
	
	
}
