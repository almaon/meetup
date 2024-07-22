package com.example.meetup.meetings.application.meetings;

import java.util.UUID;


import com.example.meetup.meetings.base.application.CommandBase;
import com.example.meetup.meetings.base.application.ICommandHandler;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RemoveMeetingAttendeeCommand extends CommandBase {

	@NotNull
	private UUID meetingId;
	@NotNull
	private UUID attendeeId;
	private String removingReason;
    	
    @Override
    public Class<? extends ICommandHandler> getHandlerType() {
        return RemoveMeetingAttendeeCommandHandler.class;
    }

}
