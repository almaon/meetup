package com.example.meetup.meetings.application.meetingComments;


import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.NoArgsConstructor;

import com.example.meetup.meetings.base.application.CommandBase;
import com.example.meetup.meetings.base.application.ICommandHandler;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddMeetingCommentLikeCommand extends CommandBase {

	@NotNull
	private UUID meetingCommentId;
    	
    @Override
    public Class<? extends ICommandHandler> getHandlerType() {
        return AddMeetingCommentLikeCommandHandler.class;
    }

}
