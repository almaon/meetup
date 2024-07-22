package com.example.meetup.meetings.application.members;


import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.NoArgsConstructor;

import com.example.meetup.meetings.base.application.CommandBase;
import com.example.meetup.meetings.base.application.ICommandHandler;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateMemberCommand extends CommandBase {

	private UUID memberId;
	private String login;
	private String lastName;
	private String firstName;
	private String email;
	private LocalDateTime registerDate;
    	
    @Override
    public Class<? extends ICommandHandler> getHandlerType() {
        return CreateMemberCommandHandler.class;
    }

}
