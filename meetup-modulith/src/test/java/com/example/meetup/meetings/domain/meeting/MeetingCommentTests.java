package com.example.meetup.meetings.domain.meeting;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.example.meetup.meetings.base.SystemClock;
import com.example.meetup.meetings.domain.meeting.Term;
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
import com.example.meetup.meetings.domain.meetingMemberCommentLike.events.MeetingCommentLikedEvent;
import com.example.meetup.meetings.domain.meetingMemberCommentLike.events.MeetingCommentUnlikedEvent;
import com.example.meetup.meetings.domain.member.MeetingGroupMemberData;
import com.example.meetup.meetings.domain.member.MemberId;



public class MeetingCommentTests extends MeetingTestBase {

	@Test
	public void addComment_WhenDataIsValid_IsSuccessful() {
		var commentAuthorId = new MemberId(UUID.randomUUID());
		var meetingTestData = createMeetingTestData(new MeetingTestDataOptionsBuilder()
				.setRvspTerm(new Term(SystemClock.now().minusHours(1), SystemClock.now().plusHours(2)))
				.setAttendees(List.of(commentAuthorId))
				.createMeetingTestDataOptions());
		var comment = "Great meeting!";

		var meetingComment = meetingTestData.getMeeting().addComment(commentAuthorId, comment,
				meetingTestData.getMeetingGroup(), meetingTestData.getMeetingCommentingConfiguration());

		publishedDomainEvents.addAll(meetingComment.getDomainEvents());
		meetingComment.clearDomainEvents();
	        
		var meetingCommentCreatedEvent = assertPublishedDomainEvent(MeetingCommentAddedEvent.class);
		assertThat(meetingCommentCreatedEvent.getMeetingCommentId(), equalTo(meetingComment.getMeetingCommentId().getValue()));
		assertThat(meetingCommentCreatedEvent.getMeetingId(), equalTo(meetingTestData.getMeeting().getMeetingId().getValue()));
		assertThat(meetingCommentCreatedEvent.getComment(), equalTo(comment));
	}

	@Test
	public void addComment_WhenAuthorIsNotMeetingGroupMember_BreaksCommentCanBeAddedOnlyByMeetingGroupMemberRule() {
		var meetingTestData = createMeetingTestData(new MeetingTestDataOptionsBuilder()
				.createMeetingTestDataOptions());

		assertBrokenRule(() -> {
			meetingTestData.getMeeting().addComment(new MemberId(UUID.randomUUID()), "Bad meeting!",
					meetingTestData.getMeetingGroup(), meetingTestData.getMeetingCommentingConfiguration());
		}, CommentCanBeAddedOnlyByMeetingGroupMemberRule.class);
	}

	static Stream<String> nullAndEmptyString() {
		return Stream.of("", null);
	}

	@ParameterizedTest
	@MethodSource("nullAndEmptyString")
	public void addComment_WhenTextIsEmpty_BreaksCommentTextMustBeProvidedRule(String missingComment) {
		var commentAuthorId = new MemberId(UUID.randomUUID());
		var meetingTestData = createMeetingTestData(new MeetingTestDataOptionsBuilder()
				.setRvspTerm(new Term(SystemClock.now().minusHours(1), SystemClock.now().plusHours(2)))
				.setAttendees(List.of(commentAuthorId))
				.createMeetingTestDataOptions());

		assertBrokenRule(() -> {
			meetingTestData.getMeeting().addComment(commentAuthorId, missingComment, meetingTestData.getMeetingGroup(),
					meetingTestData.getMeetingCommentingConfiguration());
		}, CommentTextMustBeProvidedRule.class);
	}

	@Test
	public void addComment_WhenMeetingCommentingDisabled_BreaksCommentCanBeCreatedOnlyIfCommentingForMeetingEnabledRule() {
		// Arrange
		var commentAuthorId = new MemberId(UUID.randomUUID());
		var meetingTestData = createMeetingTestData(new MeetingTestDataOptionsBuilder()
				.setRvspTerm(new Term(SystemClock.now().minusHours(1), SystemClock.now().plusHours(2)))
				.setAttendees(List.of(commentAuthorId))
				.setIsMeetingCommentingEnabled(false)
				.createMeetingTestDataOptions());

		// Assert
		assertBrokenRule(() -> {
			// Act
			meetingTestData.getMeeting().addComment(commentAuthorId, "I appreciate your work!",
					meetingTestData.getMeetingGroup(), meetingTestData.getMeetingCommentingConfiguration());
		}, CommentCanBeCreatedOnlyIfCommentingForMeetingEnabledRule.class);
	}

	@Test
	public void editComment_IsSuccessful() {
		var commentAuthorId = new MemberId(UUID.randomUUID());
		var meetingTestData = createMeetingTestData(new	MeetingTestDataOptionsBuilder()
				.setRvspTerm(new Term(SystemClock.now().minusHours(1), SystemClock.now().plusHours(2)))
				.setAttendees(List.of(commentAuthorId))
				.createMeetingTestDataOptions());
		var meetingComment = meetingTestData.getMeeting().addComment(commentAuthorId, "Great meeting!",
				meetingTestData.getMeetingGroup(), meetingTestData.getMeetingCommentingConfiguration());

		clearPublishedDomainEventsList();

		var editedComment = "Wonderful!";

		meetingComment.edit(commentAuthorId, editedComment, meetingTestData.getMeetingCommentingConfiguration());

		// Assert
		publishedDomainEvents.addAll(meetingComment.getDomainEvents());
		meetingComment.clearDomainEvents();
		
		var meetingCommentEditedEvent = assertPublishedDomainEvent(
				MeetingCommentEditedEvent.class);

		assertThat(meetingCommentEditedEvent.getMeetingCommentId(), equalTo(meetingComment.getMeetingCommentId().getValue()));
		assertThat(meetingCommentEditedEvent.getEditedComment(), equalTo(editedComment));
	}

	@Test
	public void editComment_ByNoAuthor_BreaksMeetingCommentCanBeEditedOnlyByAuthor() {
		var commentAuthorId = new MemberId(UUID.randomUUID());
		var meetingTestData = createMeetingTestData(new MeetingTestDataOptionsBuilder()
				.setRvspTerm(new Term(SystemClock.now().minusHours(1), SystemClock.now().plusHours(2)))
				.setAttendees(List.of(commentAuthorId))
				.createMeetingTestDataOptions());
		var meetingComment = meetingTestData.getMeeting().addComment(commentAuthorId, "Great meeting!",
				meetingTestData.getMeetingGroup(), meetingTestData.getMeetingCommentingConfiguration());
		clearPublishedDomainEventsList();

		var editedComment = "Wonderful!";

		assertBrokenRule(() -> {
			meetingComment.edit(new MemberId(UUID.randomUUID()), editedComment,
					meetingTestData.getMeetingCommentingConfiguration());
		}, MeetingCommentCanBeEditedOnlyByAuthorRule.class);
	}

	@ParameterizedTest
	@MethodSource("nullAndEmptyString")
	public void editComment_WhenNewTextIsEmpty_BreaksCommentTextMustBeProvidedRule(String missingComment) {
		// Arrange
		var authorId = new MemberId(UUID.randomUUID());
		var meetingTestData = createMeetingTestData(new MeetingTestDataOptionsBuilder()
				.setRvspTerm(new Term(SystemClock.now().minusHours(1), SystemClock.now().plusHours(2)))
				.setAttendees(List.of(authorId))
				.createMeetingTestDataOptions());

		var meetingComment = meetingTestData.getMeeting().addComment(authorId, "Great meeting!",
				meetingTestData.getMeetingGroup(), meetingTestData.getMeetingCommentingConfiguration());
		clearPublishedDomainEventsList();

		assertBrokenRule(() -> {

			meetingComment.edit(new MemberId(UUID.randomUUID()), missingComment,
					meetingTestData.getMeetingCommentingConfiguration());
		}, CommentTextMustBeProvidedRule.class);
	}

	@Test
	public void editComment_WhenMeetingCommentingDisabled_BreaksCommentCanBeEditedOnlyIfCommentingForMeetingEnabledRule() {
		// Arrange
		var groupOrganizerId = new MemberId(UUID.randomUUID());
		var commentAuthorId = new MemberId(UUID.randomUUID());
		var meetingTestData = createMeetingTestData(new MeetingTestDataOptionsBuilder()
				.setCreatorId(groupOrganizerId)
				.setRvspTerm(new Term(SystemClock.now().minusHours(1), SystemClock.now().plusHours(2)))
				.setAttendees(List.of(commentAuthorId))
				.createMeetingTestDataOptions());

		var meetingComment = meetingTestData.getMeeting().addComment(commentAuthorId, "It was good.",
				meetingTestData.getMeetingGroup(), meetingTestData.getMeetingCommentingConfiguration());
		meetingTestData.getMeetingCommentingConfiguration().disableCommenting(groupOrganizerId,
				meetingTestData.getMeetingGroup());

		assertBrokenRule(() -> {
			meetingComment.edit(commentAuthorId, "It was bad.", meetingTestData.getMeetingCommentingConfiguration());
		}, CommentCanBeEditedOnlyIfCommentingForMeetingEnabledRule.class);
	}

	@Test
	public void removeComment_IsSuccessful() {
		var removingMemberId = new MemberId(UUID.randomUUID());
		var meetingTestData = createMeetingTestData(new MeetingTestDataOptionsBuilder()
				.setRvspTerm(new Term(SystemClock.now().minusHours(1), SystemClock.now().plusHours(2)))
				.setAttendees(List.of(removingMemberId))
				.createMeetingTestDataOptions());

		var meetingComment = meetingTestData.getMeeting().addComment(removingMemberId, "Great meeting!",
				meetingTestData.getMeetingGroup(), meetingTestData.getMeetingCommentingConfiguration());
		clearPublishedDomainEventsList();

		meetingComment.remove(removingMemberId, meetingTestData.getMeetingGroup(), null);

		publishedDomainEvents.addAll(meetingComment.getDomainEvents());
		meetingComment.clearDomainEvents();
		
		var meetingCommentRemovedEvent = assertPublishedDomainEvent(MeetingCommentRemovedEvent.class);
		assertThat(meetingCommentRemovedEvent.getMeetingCommentId(), equalTo(meetingComment.getMeetingCommentId().getValue()));
	}

//	@Test
//	public void removeComment_ByNoAuthorNoOrganizer_BreaksMeetingCommentCanBeRemovedOnlyByAuthorOrGroupOrganizerRule() {
//		// Arrange
//		var commentAuthorId = new MemberId(UUID.randomUUID());
//		var groupCreatorId = new MemberId(UUID.randomUUID());
//
//		var meetingTestData = createMeetingTestData(new MeetingTestDataOptionsBuilder()
//				.setCreatorId(groupCreatorId)
//				.setRvspTerm(new Term(SystemClock.now().minusHours(1), SystemClock.now().plusHours(2)))
//				.setAttendees(List.of(commentAuthorId))
//				.createMeetingTestDataOptions());
//
//		var meetingComment = meetingTestData.getMeeting().addComment(commentAuthorId, "Great meeting!",
//				meetingTestData.getMeetingGroup(), meetingTestData.getMeetingCommentingConfiguration());
//
//		assertBrokenRule(() -> {
//			meetingComment.remove(new MemberId(UUID.randomUUID()), meetingTestData.getMeetingGroup(), null);
//		}, MeetingCommentCanBeRemovedOnlyByAuthorOrGroupOrganizerRule.class);
//	}

//	@Test
//	public void removeComment_ByAuthor_BreaksRemovingReasonCanBeProvidedOnlyByGroupOrganizer() {
//		// Arrange
//		var commentAuthorId = new MemberId(UUID.randomUUID());
//		var meetingTestData = createMeetingTestData(new	MeetingTestDataOptionsBuilder()
//				.setRvspTerm(new Term(SystemClock.now().minusHours(1), SystemClock.now().plusHours(2)))
//				.setAttendees(List.of(commentAuthorId))
//				.createMeetingTestDataOptions());
//
//		var meetingComment = meetingTestData.getMeeting().addComment(commentAuthorId, "Great meeting!",
//				meetingTestData.getMeetingGroup(), meetingTestData.getMeetingCommentingConfiguration());
//
//		assertBrokenRule(() -> {
//			meetingComment.remove(commentAuthorId, meetingTestData.getMeetingGroup(), "I don't like the comment.");
//		}, RemovingReasonCanBeProvidedOnlyByGroupOrganizerRule.class);
//	}

	@Test
	public void addReplyToComment_WhenDataIsValid_IsSuccessful() {
		var commentAuthorId = new MemberId(UUID.randomUUID());
		var replyAuthorId = new MemberId(UUID.randomUUID());
		var meetingTestData = createMeetingTestData(new MeetingTestDataOptionsBuilder()
				.setRvspTerm(new Term(SystemClock.now().minusHours(1), SystemClock.now().plusHours(2)))
				.setAttendees(List.of(commentAuthorId, replyAuthorId))
				.createMeetingTestDataOptions());

		var meetingComment = meetingTestData.getMeeting().addComment(commentAuthorId, "Great meeting!",
				meetingTestData.getMeetingGroup(), meetingTestData.getMeetingCommentingConfiguration());
		clearPublishedDomainEventsList();

		var reply = "Exactly!";

		var replyToComment = meetingComment.reply(replyAuthorId, reply, meetingTestData.getMeetingGroup(),
				meetingTestData.getMeetingCommentingConfiguration());

		publishedDomainEvents.addAll(replyToComment.getDomainEvents());
		replyToComment.clearDomainEvents();
		
		var replyToMeetingCommentAddedEvent =
				assertPublishedDomainEvent(ReplyToMeetingCommentAddedEvent.class);
		assertThat(replyToMeetingCommentAddedEvent.getMeetingCommentId(), equalTo(replyToComment.getMeetingCommentId().getValue()));
		assertThat(replyToMeetingCommentAddedEvent.getInReplyToCommentId(), equalTo(meetingComment.getMeetingCommentId().getValue()));
		assertThat(replyToMeetingCommentAddedEvent.getReply(), equalTo(reply));
	}

	@Test
	public void addReplyToComment_WhenAuthorIsNotMeetingGroupMember_BreaksCommentCanBeAddedOnlyByMeetingGroupMemberRule() {
		var commentAuthorId = new MemberId(UUID.randomUUID());
		var meetingTestData = createMeetingTestData(new	MeetingTestDataOptionsBuilder()
				.setRvspTerm(new Term(SystemClock.now().minusHours(1), SystemClock.now().plusHours(2)))
				.setAttendees(List.of(commentAuthorId))
				.createMeetingTestDataOptions());

		var meetingComment = meetingTestData.getMeeting().addComment(commentAuthorId, "Great meeting!",
				meetingTestData.getMeetingGroup(), meetingTestData.getMeetingCommentingConfiguration());

		assertBrokenRule(() -> {
			meetingComment.reply(new MemberId(UUID.randomUUID()), "Exactly!", meetingTestData.getMeetingGroup(),
					meetingTestData.getMeetingCommentingConfiguration());
		}, CommentCanBeAddedOnlyByMeetingGroupMemberRule.class);
	}

	@ParameterizedTest
	@MethodSource("nullAndEmptyString")
	public void addReplyToComment_WhenTextIsEmpty_BreaksCommentTextMustBeProvidedRule(String missingReply) {
		var commentAuthorId = new MemberId(UUID.randomUUID());
		var replyAuthorId = new MemberId(UUID.randomUUID());
		var meetingTestData = createMeetingTestData(new MeetingTestDataOptionsBuilder()
				.setRvspTerm(new Term(SystemClock.now().minusHours(1), SystemClock.now().plusHours(2)))
				.setAttendees(List.of(commentAuthorId, replyAuthorId))
				.createMeetingTestDataOptions());

		var meetingComment = meetingTestData.getMeeting().addComment(commentAuthorId, "Great meeting!",
				meetingTestData.getMeetingGroup(), meetingTestData.getMeetingCommentingConfiguration());

		assertBrokenRule(() -> {
			meetingComment.reply(replyAuthorId, missingReply, meetingTestData.getMeetingGroup(),
					meetingTestData.getMeetingCommentingConfiguration());
		}, CommentTextMustBeProvidedRule.class);
	}

	@Test
	public void addReplyToComment_WhenMeetingCommentingDisabled_BreaksCommentCanBeCreatedOnlyIfCommentingForMeetingEnabledRule() {
		var creatorId = new MemberId(UUID.randomUUID());
		var commentAuthorId = new MemberId(UUID.randomUUID());
		var replyAuthorId = new MemberId(UUID.randomUUID());

		var meetingTestData = createMeetingTestData(new MeetingTestDataOptionsBuilder()
				.setCreatorId(creatorId)
				.setRvspTerm(new Term(SystemClock.now().minusHours(1), SystemClock.now().plusHours(2)))
				.setAttendees(List.of(commentAuthorId, replyAuthorId))
				.createMeetingTestDataOptions());
		var meetingComment = meetingTestData.getMeeting().addComment(commentAuthorId, "Great meeting!",
				meetingTestData.getMeetingGroup(), meetingTestData.getMeetingCommentingConfiguration());

		meetingTestData.getMeetingCommentingConfiguration().disableCommenting(creatorId,
				meetingTestData.getMeetingGroup());

		assertBrokenRule(() -> {
			meetingComment.reply(replyAuthorId, "Exactly!", meetingTestData.getMeetingGroup(),
					meetingTestData.getMeetingCommentingConfiguration());
		}, CommentCanBeCreatedOnlyIfCommentingForMeetingEnabledRule.class);
	}

	@Test
	public void addLikeToComment_WhenDataIsValid_IsSuccessful() {
		var commentAuthorId = new MemberId(UUID.randomUUID());
		var likerId = new MemberId(UUID.randomUUID());
		var meetingTestData = createMeetingTestData(new MeetingTestDataOptionsBuilder()
				.setRvspTerm(new Term(SystemClock.now().minusHours(1), SystemClock.now().plusHours(2)))
				.setAttendees(List.of(commentAuthorId, likerId))
				.createMeetingTestDataOptions());

		var meetingComment = meetingTestData.getMeeting().addComment(commentAuthorId, "Great meeting!",
				meetingTestData.getMeetingGroup(), meetingTestData.getMeetingCommentingConfiguration());

		var meetingMemberCommentLike = meetingComment.like(likerId,
				new MeetingGroupMemberData(likerId, meetingTestData.getMeetingGroup().getMeetingGroupId()), 0);

		publishedDomainEvents.addAll(meetingMemberCommentLike.getDomainEvents());
		meetingMemberCommentLike.clearDomainEvents();
		
		var meetingCommentLikedEvent =
				assertPublishedDomainEvent(MeetingCommentLikedEvent.class);
		assertThat(meetingCommentLikedEvent.getMeetingCommentId(), equalTo(meetingComment.getMeetingCommentId().getValue()));
		assertThat(meetingCommentLikedEvent.getLikerId(), equalTo(likerId.getValue()));
	}

	@Test
	public void addLikeToComment_WhenLikerIsNotGroupMember_BreaksCommentCanBeLikedOnlyByMeetingGroupMemberRule() {
		var commentAuthorId = new MemberId(UUID.randomUUID());
		var meetingTestData = createMeetingTestData(new MeetingTestDataOptionsBuilder()
				.setRvspTerm(new Term(SystemClock.now().minusHours(1), SystemClock.now().plusHours(2)))
				.setAttendees(List.of(commentAuthorId))
				.createMeetingTestDataOptions());

		var meetingComment = meetingTestData.getMeeting().addComment(commentAuthorId, "Great meeting!",
				meetingTestData.getMeetingGroup(), meetingTestData.getMeetingCommentingConfiguration());

		assertBrokenRule(() -> {
			meetingComment.like(new MemberId(UUID.randomUUID()), null, 0);
		}, CommentCanBeLikedOnlyByMeetingGroupMemberRule.class);
	}

	@Test
	public void addLikeToComment_WhenTheCommentIsAlreadyLikedByTheMember_BreaksCommentCannotBeLikedByTheSameMemberMoreThanOnceRule() {
		var commentAuthorId = new MemberId(UUID.randomUUID());
		var likerId = new MemberId(UUID.randomUUID());

		var meetingTestData = createMeetingTestData(new MeetingTestDataOptionsBuilder()
				.setRvspTerm(new Term(SystemClock.now().minusHours(1), SystemClock.now().plusHours(2)))
				.setAttendees(List.of(commentAuthorId, likerId))
				.createMeetingTestDataOptions());

		var meetingComment = meetingTestData.getMeeting().addComment(commentAuthorId, "Great meeting!",
				meetingTestData.getMeetingGroup(), meetingTestData.getMeetingCommentingConfiguration());

		assertBrokenRule(() -> {
			meetingComment.like(likerId, new MeetingGroupMemberData(likerId, meetingTestData.getMeetingGroup().getMeetingGroupId()),
					1);
		}, CommentCannotBeLikedByTheSameMemberMoreThanOnceRule.class);
	}

	@Test
	public void removeLike_WhenDataIsValid_IsSuccessful() {
		var commentAuthorId = new MemberId(UUID.randomUUID());
		var likerId = new MemberId(UUID.randomUUID());
		var meetingTestData = createMeetingTestData(new MeetingTestDataOptionsBuilder()
				.setRvspTerm(new Term(SystemClock.now().minusHours(1), SystemClock.now().plusHours(2)))
				.setAttendees(List.of(commentAuthorId, likerId))
				.createMeetingTestDataOptions());

		var meetingComment = meetingTestData.getMeeting().addComment(commentAuthorId, "Great meeting!",
				meetingTestData.getMeetingGroup(), meetingTestData.getMeetingCommentingConfiguration());

		var commentLike = meetingComment.like(likerId,
				new MeetingGroupMemberData(likerId, meetingTestData.getMeetingGroup().getMeetingGroupId()), 0);
		clearPublishedDomainEventsList();

		commentLike.remove();

		publishedDomainEvents.addAll(commentLike.getDomainEvents());
		commentLike.clearDomainEvents();
		
		var meetingCommentUnlikedEvent = assertPublishedDomainEvent(MeetingCommentUnlikedEvent.class);
		assertThat(meetingCommentUnlikedEvent.getMeetingCommentId(), equalTo(meetingComment.getMeetingCommentId().getValue()));
		assertThat(meetingCommentUnlikedEvent.getLikerId(), equalTo(likerId.getValue()));
	}
}
