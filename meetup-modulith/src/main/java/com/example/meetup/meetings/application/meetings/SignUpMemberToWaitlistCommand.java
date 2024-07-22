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
public class SignUpMemberToWaitlistCommand extends CommandBase {

	@NotNull
	private UUID meetingId;
    	
    @Override
    public Class<? extends ICommandHandler> getHandlerType() {
        return SignUpMemberToWaitlistCommandHandler.class;
    }

}
