package com.example.meetup.meetings.application.meetingComments;


import org.springframework.stereotype.Component;

import com.example.meetup.meetings.base.application.IAsyncCommandHandler;
import com.example.meetup.meetings.base.domain.IAggregateStore;
import com.example.meetup.meetings.domain.meetingComment.MeetingComment;
import com.example.meetup.meetings.domain.meetingComment.MeetingCommentId;
import com.example.meetup.meetings.domain.meetingCommentingConfiguration.MeetingCommentingConfiguration;
import com.example.meetup.meetings.domain.meetingCommentingConfiguration.MeetingCommentingConfigurationId;
import com.example.meetup.meetings.infrastructure.MeetingContext;
import com.example.meetup.meetings.infrastructure.MeetingsQueryDispatcher;

import lombok.RequiredArgsConstructor;

    
@Component
@RequiredArgsConstructor
public class EditMeetingCommentCommandHandler implements IAsyncCommandHandler<EditMeetingCommentCommand> {

	private final IAggregateStore aggregateStore;

	private final MeetingContext context;
	private final MeetingsQueryDispatcher queryDispatcher;

	@Override
	public void handle(EditMeetingCommentCommand command) {

		MeetingComment meetingComment = aggregateStore.load(new MeetingCommentId(command.getMeetingCommentId()), MeetingComment.class);
    	
		MeetingCommentingConfiguration meetingCommentingConfiguration = aggregateStore.load(new MeetingCommentingConfigurationId(meetingComment.getMeetingId().getValue()), MeetingCommentingConfiguration.class);
		
    	meetingComment.edit(
    			context.principalId(), 
    			command.getEditedComment(), 
    			meetingCommentingConfiguration);
		
		aggregateStore.save(meetingComment);

	}	
    	
}
