package com.example.meetup.meetings.application.meetingComments;


import java.util.UUID;

import org.springframework.stereotype.Component;

import com.example.meetup.meetings.application.meetings.view.GetMeetingsByIdQuery;
import com.example.meetup.meetings.application.meetings.view.MeetingsView;
import com.example.meetup.meetings.base.application.ISyncCommandHandler;
import com.example.meetup.meetings.base.domain.IAggregateStore;
import com.example.meetup.meetings.domain.meetingComment.MeetingComment;
import com.example.meetup.meetings.domain.meetingComment.MeetingCommentId;
import com.example.meetup.meetings.domain.meetingCommentingConfiguration.MeetingCommentingConfiguration;
import com.example.meetup.meetings.domain.meetingCommentingConfiguration.MeetingCommentingConfigurationId;
import com.example.meetup.meetings.domain.meetingGroup.MeetingGroup;
import com.example.meetup.meetings.domain.meetingGroup.MeetingGroupId;
import com.example.meetup.meetings.infrastructure.MeetingContext;
import com.example.meetup.meetings.infrastructure.MeetingsQueryDispatcher;

import lombok.RequiredArgsConstructor;

    
@Component
@RequiredArgsConstructor
public class AddReplyToMeetingCommentCommandHandler implements ISyncCommandHandler<AddReplyToMeetingCommentCommand, UUID> {
	
	private final IAggregateStore aggregateStore;

	private final MeetingContext context;
	private final MeetingsQueryDispatcher queryDispatcher;

	
	@Override
	public UUID handle(AddReplyToMeetingCommentCommand command) {	

		MeetingComment meetingComment = aggregateStore.load(new MeetingCommentId(command.getInReplyToCommentId()), MeetingComment.class);
    	
		MeetingsView meetingsView = queryDispatcher.executeQuery(new GetMeetingsByIdQuery(meetingComment.getMeetingId().getValue()));
		
		MeetingGroup meetingGroup = aggregateStore.load(new MeetingGroupId(meetingsView.getMeetingGroupId()), MeetingGroup.class);

		MeetingCommentingConfiguration meetingCommentingConfiguration = aggregateStore.load(new MeetingCommentingConfigurationId(meetingComment.getMeetingId().getValue()), MeetingCommentingConfiguration.class);

        var replyToComment = meetingComment.reply(
        		context.principalId(), 
        		command.getReply(), 
        		meetingGroup, 
        		meetingCommentingConfiguration);

		aggregateStore.save(replyToComment);
		return replyToComment.getMeetingCommentId().getValue();

	}	
    	
}
