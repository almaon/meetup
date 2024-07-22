package com.example.meetup.meetings.domain.meetingComment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.example.meetup.meetings.base.SystemClock;
import com.example.meetup.meetings.base.domain.Aggregate;
import com.example.meetup.meetings.base.domain.Entity;
import com.example.meetup.meetings.base.domain.IDomainEvent;
import com.example.meetup.meetings.domain.meeting.MeetingId;
import com.example.meetup.meetings.domain.meetingComment.events.MeetingCommentAddedEvent;
import com.example.meetup.meetings.domain.meetingComment.events.MeetingCommentEditedEvent;
import com.example.meetup.meetings.domain.meetingComment.events.MeetingCommentRemovedEvent;
import com.example.meetup.meetings.domain.meetingComment.events.ReplyToMeetingCommentAddedEvent;
import com.example.meetup.meetings.domain.meetingComment.rules.CommentCanBeAddedOnlyByMeetingGroupMemberRule;
import com.example.meetup.meetings.domain.meetingComment.rules.CommentCanBeCreatedOnlyIfCommentingForMeetingEnabledRule;
import com.example.meetup.meetings.domain.meetingComment.rules.CommentCanBeEditedOnlyIfCommentingForMeetingEnabledRule;
import com.example.meetup.meetings.domain.meetingComment.rules.CommentCanBeLikedOnlyByMeetingGroupMemberRule;
import com.example.meetup.meetings.domain.meetingComment.rules.CommentCannotBeLikedByTheSameMemberMoreThanOnceRule;
import com.example.meetup.meetings.domain.meetingComment.rules.CommentTextMustBeProvidedRule;
import com.example.meetup.meetings.domain.meetingComment.rules.MeetingCommentCanBeEditedOnlyByAuthorRule;
import com.example.meetup.meetings.domain.meetingComment.rules.MeetingCommentCanBeRemovedOnlyByAuthorOrGroupOrganizerRule;
import com.example.meetup.meetings.domain.meetingComment.rules.RemovingReasonCanBeProvidedOnlyByGroupOrganizerRule;
import com.example.meetup.meetings.domain.meetingCommentingConfiguration.MeetingCommentingConfiguration;
import com.example.meetup.meetings.domain.meetingGroup.MeetingGroup;
import com.example.meetup.meetings.domain.meetingMemberCommentLike.MeetingMemberCommentLike;
import com.example.meetup.meetings.domain.member.MeetingGroupMemberData;
import com.example.meetup.meetings.domain.member.MemberId;

import lombok.Getter;
import lombok.Setter;

@Getter
public class MeetingComment extends Aggregate {
	
	// business id
	@Setter // for testing
	protected MeetingCommentId meetingCommentId;
	
	public String getStreamId() {
		return "Meetings-MeetingComment-" + meetingCommentId.getValue();
	}
		
	protected MeetingId meetingId;
	protected MemberId authorId;
	protected MeetingCommentId inReplyToCommentId;
	protected String comment;
	protected LocalDateTime createDate;
	protected LocalDateTime editDate;
	protected Boolean isRemoved;
	protected String removedByReason;
	
	public MeetingComment() {
	}
	
	public MeetingComment(MeetingId meetingId, MemberId authorId, String comment, MeetingCommentId inReplyToCommentId, MeetingCommentingConfiguration meetingCommentingConfiguration, MeetingGroup meetingGroup) {
        checkRule(new CommentTextMustBeProvidedRule(comment));
        checkRule(new CommentCanBeCreatedOnlyIfCommentingForMeetingEnabledRule(meetingCommentingConfiguration));
        checkRule(new CommentCanBeAddedOnlyByMeetingGroupMemberRule(authorId, meetingGroup));
        
		if (inReplyToCommentId == null) {
			var meetingCommentAdded = new MeetingCommentAddedEvent(
					UUID.randomUUID(),
					meetingId.getValue(),
					comment,
					authorId.getValue(),
					SystemClock.now());
			
			apply(meetingCommentAdded);
			addDomainEvent(meetingCommentAdded);
			
		} else {
			var replyToMeetingCommentAdded = new ReplyToMeetingCommentAddedEvent(
					UUID.randomUUID(),
					inReplyToCommentId.getValue(),
					comment,
					meetingId.getValue(),
					authorId.getValue(),
					SystemClock.now());
			
			apply(replyToMeetingCommentAdded);
			addDomainEvent(replyToMeetingCommentAdded);			
		}
	}
	
	public void edit(MemberId editorId, String editedComment, MeetingCommentingConfiguration meetingCommentingConfiguration) {
        checkRule(new CommentTextMustBeProvidedRule(editedComment));
        checkRule(new MeetingCommentCanBeEditedOnlyByAuthorRule(authorId, editorId));
        checkRule(new CommentCanBeEditedOnlyIfCommentingForMeetingEnabledRule(meetingCommentingConfiguration));
        
		var meetingCommentEdited = new MeetingCommentEditedEvent(
			meetingCommentId.getValue(),
			editedComment,
			SystemClock.now());
 
		apply(meetingCommentEdited);
		addDomainEvent(meetingCommentEdited);
	}	
	public void remove(MemberId removingMemberId, MeetingGroup meetingGroup, String reason) {
		checkRule(new MeetingCommentCanBeRemovedOnlyByAuthorOrGroupOrganizerRule(meetingGroup, authorId, removingMemberId));
        checkRule(new RemovingReasonCanBeProvidedOnlyByGroupOrganizerRule(meetingGroup, removingMemberId, reason));
        
		var meetingCommentRemoved = new MeetingCommentRemovedEvent(
			meetingCommentId.getValue(),
			reason);
 
		apply(meetingCommentRemoved);
		addDomainEvent(meetingCommentRemoved);
	}	
	
	public MeetingComment reply(MemberId replierId, String reply, MeetingGroup MeetingGroup, MeetingCommentingConfiguration MeetingCommentingConfiguration) {

		return new MeetingComment(
				meetingId, 
				replierId, 
				reply, 
				meetingCommentId, 
				MeetingCommentingConfiguration, 
				MeetingGroup);
	}	
	public MeetingMemberCommentLike like(MemberId likerId, MeetingGroupMemberData likerMeetingGroupMember, int meetingMemberCommentLikesCount) {
		checkRule(new CommentCanBeLikedOnlyByMeetingGroupMemberRule(likerMeetingGroupMember));
        checkRule(new CommentCannotBeLikedByTheSameMemberMoreThanOnceRule(meetingMemberCommentLikesCount));

		return new MeetingMemberCommentLike(meetingCommentId, likerId);
	}	
	
	
	private boolean when(ReplyToMeetingCommentAddedEvent event) {
		meetingCommentId = new MeetingCommentId(event.getMeetingCommentId());
		meetingId = new MeetingId(event.getMeetingId());
		comment = event.getReply();
		authorId = new MemberId(event.getAuthorId());
		createDate = event.getCreateDate();
		inReplyToCommentId = new MeetingCommentId(event.getInReplyToCommentId());
		
		return true;
	}
	
	private boolean when(MeetingCommentRemovedEvent event) {
		isRemoved = true;
		removedByReason = event.getReason();
		
		return true;
	}
	
	private boolean when(MeetingCommentAddedEvent event) {
		meetingCommentId = new MeetingCommentId(event.getMeetingCommentId());
		meetingId = new MeetingId(event.getMeetingId());
		comment = event.getComment();
		authorId = new MemberId(event.getAuthorId());
		createDate = event.getCreateDate();
		
		return true;
	}
	
	private boolean when(MeetingCommentEditedEvent event) {
		comment = event.getEditedComment();
		editDate = event.getEditDate();
		
		return true;
	}
	
	@Override
	protected boolean apply(IDomainEvent event) {
	
		var processed = false;

		if (event instanceof ReplyToMeetingCommentAddedEvent castedEvent) {
			processed = when(castedEvent);
		}
		else if (event instanceof MeetingCommentRemovedEvent castedEvent) {
			processed = when(castedEvent);
		}
		else if (event instanceof MeetingCommentAddedEvent castedEvent) {
			processed = when(castedEvent);
		}
		else if (event instanceof MeetingCommentEditedEvent castedEvent) {
			processed = when(castedEvent);
		}
		
		if (processed)
			incrementVersion();
			
		return processed;
	}
	
	@Override
	public List<Entity> getDirectChildEntities() {
		var entities = new ArrayList<Entity>();
		return entities;
	}
	
	
}
