package com.example.meetup.meetings.application.meetingCommentingConfigurations;


import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.NoArgsConstructor;

import com.example.meetup.meetings.base.application.CommandBase;
import com.example.meetup.meetings.base.application.ICommandHandler;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnableMeetingCommentingConfigurationCommand extends CommandBase {

	@NotNull
	private UUID meetingId;
    	
    @Override
    public Class<? extends ICommandHandler> getHandlerType() {
        return EnableMeetingCommentingConfigurationCommandHandler.class;
    }

}
