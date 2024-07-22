package com.example.meetup.meetings.application.meetingComments;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.example.meetup.meetings.application.meetingCommentingConfigurations.view.GetMeetingCommentingConfigurationsByIdQuery;
import com.example.meetup.meetings.application.meetingCommentingConfigurations.view.MeetingCommentingConfigurationsView;
import com.example.meetup.meetings.base.application.ISyncCommandHandler;
import com.example.meetup.meetings.base.domain.IAggregateStore;
import com.example.meetup.meetings.domain.meeting.Meeting;
import com.example.meetup.meetings.domain.meetingCommentingConfiguration.MeetingCommentingConfiguration;
import com.example.meetup.meetings.domain.meetingCommentingConfiguration.MeetingCommentingConfigurationId;
import com.example.meetup.meetings.domain.meetingGroup.MeetingGroup;
import com.example.meetup.meetings.domain.meetingGroup.MeetingGroupId;
import com.example.meetup.meetings.infrastructure.MeetingContext;
import com.example.meetup.meetings.infrastructure.MeetingsQueryDispatcher;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AddMeetingCommentCommandHandler implements ISyncCommandHandler<AddMeetingCommentCommand, UUID> {

	private final IAggregateStore aggregateStore;
	private final MeetingContext context;
	private final MeetingsQueryDispatcher queryDispatcher;

	@Override
	public UUID handle(AddMeetingCommentCommand command) {
		Meeting meeting = aggregateStore.load(new MeetingGroupId(command.getMeetingId()), Meeting.class);

		MeetingGroup meetingGroup = aggregateStore.load(meeting.getMeetingGroupId(), MeetingGroup.class);

		MeetingCommentingConfigurationsView commentingConfigurationsView = queryDispatcher
				.executeQuery(new GetMeetingCommentingConfigurationsByIdQuery(command.getMeetingId()));

		MeetingCommentingConfiguration meetingCommentingConfiguration = aggregateStore.load(
				new MeetingCommentingConfigurationId(
						commentingConfigurationsView.getMeetingCommentingConfigurationId()),
				MeetingCommentingConfiguration.class);

		var meetingComment = meeting.addComment(context.principalId(), command.getComment(), meetingGroup,
				meetingCommentingConfiguration);

		aggregateStore.save(meetingComment);

		return meetingComment.getMeetingCommentId().getValue();
	}

}
