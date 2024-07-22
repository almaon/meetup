package com.example.meetup.meetings.application.meetingCommentingConfigurations;


import org.springframework.stereotype.Component;

import com.example.meetup.meetings.base.application.IAsyncCommandHandler;
import com.example.meetup.meetings.base.domain.IAggregateStore;
import com.example.meetup.meetings.domain.meeting.Meeting;
import com.example.meetup.meetings.domain.meeting.MeetingId;
import com.example.meetup.meetings.domain.meetingCommentingConfiguration.MeetingCommentingConfiguration;

import lombok.RequiredArgsConstructor;

    
@Component
@RequiredArgsConstructor
public class CreateMeetingCommentingConfigurationCommandHandler implements IAsyncCommandHandler<CreateMeetingCommentingConfigurationCommand> {

	private final IAggregateStore aggregateStore;


	@Override
	public void handle(CreateMeetingCommentingConfigurationCommand command) {

		Meeting meeting = aggregateStore.load(new MeetingId(command.getMeetingId()), Meeting.class);
		
   		var meetingCommentingConfiguration = meeting.createCommentingConfiguration();
		
		aggregateStore.save(meetingCommentingConfiguration);

	}	
    	
}
