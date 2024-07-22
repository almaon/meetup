package com.example.meetup.meetings.domain.meeting;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.example.meetup.meetings.domain.meetingCommentingConfiguration.events.MeetingCommentingConfigurationCreatedEvent;
import com.example.meetup.meetings.domain.meetingCommentingConfiguration.events.MeetingCommentingDisabledEvent;
import com.example.meetup.meetings.domain.meetingCommentingConfiguration.events.MeetingCommentingEnabledEvent;
import com.example.meetup.meetings.domain.meetingCommentingConfiguration.rules.MeetingCommentingCanBeDisabledOnlyByGroupOrganizerRule;
import com.example.meetup.meetings.domain.meetingCommentingConfiguration.rules.MeetingCommentingCanBeEnabledOnlyByGroupOrganizerRule;
import com.example.meetup.meetings.domain.member.MemberId;


public class MeetingCommentingConfigurationTests extends MeetingTestBase {

	@Test
	public void createMeetingCommentingConfiguration_IsSuccessful() {
		var meeting = createMeetingTestData(new MeetingTestDataOptionsBuilder()
				.createMeetingTestDataOptions());

		var meetingCommentingConfiguration = meeting.getMeeting().createCommentingConfiguration();

		publishedDomainEvents.addAll(meetingCommentingConfiguration.getDomainEvents());
		var meetingCommentingConfigurationCreatedEvent = assertPublishedDomainEvent(MeetingCommentingConfigurationCreatedEvent.class);
		assertThat(meetingCommentingConfigurationCreatedEvent.getMeetingId(), equalTo(meeting.getMeeting().getMeetingId().getValue()));
		assertThat(meetingCommentingConfigurationCreatedEvent.getIsEnabled(), equalTo(false));
	}

	@Test
	public void disableCommenting_IsSuccessfull() {
		// Arrange
		var organizerId = new MemberId(UUID.randomUUID());
		var meeting = createMeetingTestData(new MeetingTestDataOptionsBuilder()
				.setCreatorId(organizerId)
				.createMeetingTestDataOptions());
		var meetingCommentingConfiguration = meeting.getMeeting().createCommentingConfiguration();

		meetingCommentingConfiguration.enableCommenting(organizerId, meeting.getMeetingGroup());
		meetingCommentingConfiguration.disableCommenting(organizerId, meeting.getMeetingGroup());

		// Assert
		publishedDomainEvents.addAll(meetingCommentingConfiguration.getDomainEvents());
		var meetingCommentingDisabledEvent = assertPublishedDomainEvent(
				MeetingCommentingDisabledEvent.class);
		assertThat(meetingCommentingDisabledEvent.getMeetingId(), equalTo(meeting.getMeeting().getMeetingId().getValue()));
	}

//	@Test
//	public void disableCommenting_WhenMemberIsNotGroupOrginizer_BreakMeetingCommentingCanBeDisabledOnlyByGroupOrganizerRule() {
//		// Arrange
//		var meeting = createMeetingTestData(new MeetingTestDataOptionsBuilder()
//				.createMeetingTestDataOptions());
//		var meetingCommentingConfiguration = meeting.getMeeting().createCommentingConfiguration();
//
//		assertBrokenRule(() -> meetingCommentingConfiguration.disableCommenting(new MemberId(UUID.randomUUID()),
//				meeting.getMeetingGroup()), MeetingCommentingCanBeDisabledOnlyByGroupOrganizerRule.class);
//	}

	@Test
	public void disableCommenting_WhenCommentingAlreadyDisabled_IsIgnored() {
		var organizerId = new MemberId(UUID.randomUUID());
		var meeting = createMeetingTestData(new MeetingTestDataOptionsBuilder()
				.setCreatorId(organizerId)
				.createMeetingTestDataOptions());
		var meetingCommentingConfiguration = meeting.getMeeting().createCommentingConfiguration();
		meetingCommentingConfiguration.disableCommenting(organizerId, meeting.getMeetingGroup());

		meetingCommentingConfiguration.clearDomainEvents();

		meetingCommentingConfiguration.disableCommenting(organizerId, meeting.getMeetingGroup());

		publishedDomainEvents.addAll(meetingCommentingConfiguration.getDomainEvents());

		assertDomainEventNotPublished(MeetingCommentingDisabledEvent.class);
	}

	@Test
	public void enableCommenting_IsSuccessfull() {
		// Arrange
		var organizerId = new MemberId(UUID.randomUUID());
		var meeting = createMeetingTestData(new MeetingTestDataOptionsBuilder()
				.setCreatorId(organizerId)
				.createMeetingTestDataOptions());
		var meetingCommentingConfiguration = meeting.getMeeting().createCommentingConfiguration();

		meetingCommentingConfiguration.enableCommenting(organizerId, meeting.getMeetingGroup());

		// Assert
		publishedDomainEvents.addAll(meetingCommentingConfiguration.getDomainEvents());
		var meetingCommentingEnabledEvent = assertPublishedDomainEvent(
				MeetingCommentingEnabledEvent.class);
		assertThat(meetingCommentingEnabledEvent.getMeetingId(), equalTo(meeting.getMeeting().getMeetingId().getValue()));
	}

//	@Test
//	public void enableCommenting_WhenMemberIsNotGroupOrginizer_BreakMeetingCommentingCanBeEnabledOnlyByGroupOrganizerRule() {
//		// Arrange
//		var organizerId = new MemberId(UUID.randomUUID());
//		var meeting = createMeetingTestData(new MeetingTestDataOptionsBuilder()
//				.setCreatorId(organizerId)
//				.createMeetingTestDataOptions());
//		var meetingCommentingConfiguration = meeting.getMeeting().createCommentingConfiguration();
//
//		assertBrokenRule(() -> meetingCommentingConfiguration.enableCommenting(new MemberId(UUID.randomUUID()),
//				meeting.getMeetingGroup()), MeetingCommentingCanBeEnabledOnlyByGroupOrganizerRule.class);
//	}

	@Test
	public void enableCommenting_WhenCommentingAlreadyEnabled_IsIgnored() {
		var organizerId = new MemberId(UUID.randomUUID());
		var meeting = createMeetingTestData(new MeetingTestDataOptionsBuilder()
				.setCreatorId(organizerId)
				.createMeetingTestDataOptions());
		var meetingCommentingConfiguration = meeting.getMeeting().createCommentingConfiguration();

		meetingCommentingConfiguration.enableCommenting(organizerId, meeting.getMeetingGroup());

		publishedDomainEvents.addAll(meetingCommentingConfiguration.getDomainEvents());

		assertDomainEventNotPublished(MeetingCommentingDisabledEvent.class);
	}

}
