package com.example.meetup.meetings.application.meetingCommentingConfigurations;


import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.NoArgsConstructor;

import com.example.meetup.meetings.base.application.CommandBase;
import com.example.meetup.meetings.base.application.ICommandHandler;

import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateMeetingCommentingConfigurationCommand extends CommandBase {

	private UUID meetingId;
    	
    @Override
    public Class<? extends ICommandHandler> getHandlerType() {
        return CreateMeetingCommentingConfigurationCommandHandler.class;
    }

}
