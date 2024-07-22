package com.example.meetup.userAccess.application.addUserToAuthServer;


import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.NoArgsConstructor;

import com.example.meetup.userAccess.base.application.CommandBase;
import com.example.meetup.userAccess.base.application.ICommandHandler;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddUserToAuthServerCommand extends CommandBase {

	private UUID userId;
	private String firstName;
	private LocalDateTime registerDate;
	private String encodedPassword;
	private String lastName;
	private String email;
	private String login;
    	
    @Override
    public Class<? extends ICommandHandler> getHandlerType() {
        return AddUserToAuthServerCommandHandler.class;
    }

}
