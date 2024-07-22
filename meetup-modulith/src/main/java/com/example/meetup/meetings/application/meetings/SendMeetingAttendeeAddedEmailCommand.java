package com.example.meetup.meetings.application.meetings;


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
public class SendMeetingAttendeeAddedEmailCommand extends CommandBase {

	private UUID meetingId;
	private UUID attendeeId;
    	
    @Override
    public Class<? extends ICommandHandler> getHandlerType() {
        return SendMeetingAttendeeAddedEmailCommandHandler.class;
    }

}
