package com.example.meetup.meetings.application.meetingCommentingConfigurations;


import org.springframework.stereotype.Component;

import com.example.meetup.meetings.application.meetingCommentingConfigurations.view.GetMeetingCommentingConfigurationsByIdQuery;
import com.example.meetup.meetings.application.meetingCommentingConfigurations.view.MeetingCommentingConfigurationsView;
import com.example.meetup.meetings.application.meetings.view.GetMeetingsByIdQuery;
import com.example.meetup.meetings.application.meetings.view.MeetingsView;
import com.example.meetup.meetings.base.application.IAsyncCommandHandler;
import com.example.meetup.meetings.base.domain.IAggregateStore;
import com.example.meetup.meetings.base.infrastructure.IQueryDispatcher;
import com.example.meetup.meetings.domain.meetingCommentingConfiguration.MeetingCommentingConfiguration;
import com.example.meetup.meetings.domain.meetingCommentingConfiguration.MeetingCommentingConfigurationId;
import com.example.meetup.meetings.domain.meetingGroup.MeetingGroup;
import com.example.meetup.meetings.domain.meetingGroup.MeetingGroupId;
import com.example.meetup.meetings.infrastructure.MeetingContext;

import lombok.RequiredArgsConstructor;

    
@Component
@RequiredArgsConstructor
public class EnableMeetingCommentingConfigurationCommandHandler implements IAsyncCommandHandler<EnableMeetingCommentingConfigurationCommand> {

	private final IAggregateStore aggregateStore;

	private final IQueryDispatcher queryDispatcher;
	private final MeetingContext context;

	@Override
	public void handle(EnableMeetingCommentingConfigurationCommand command) {

		MeetingCommentingConfigurationsView meetingCommentingConfigurationsView = queryDispatcher
				.executeQuery(new GetMeetingCommentingConfigurationsByIdQuery(command.getMeetingId()));
		
		MeetingCommentingConfiguration meetingCommentingConfiguration = aggregateStore.load(
				new MeetingCommentingConfigurationId(meetingCommentingConfigurationsView.getMeetingCommentingConfigurationId()), MeetingCommentingConfiguration.class);
    	
		MeetingsView meetings = queryDispatcher.executeQuery(new GetMeetingsByIdQuery(command.getMeetingId()));
		
		MeetingGroup meetingGroup = aggregateStore.load(new MeetingGroupId(meetings.getMeetingGroupId()), MeetingGroup.class);

   		meetingCommentingConfiguration.enableCommenting(context.principalId(), meetingGroup);
		
		aggregateStore.save(meetingCommentingConfiguration);

	}	
    	
}
