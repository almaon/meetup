package com.example.meetup.userAccess.application.authenticate;


import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.NoArgsConstructor;

import com.example.meetup.userAccess.base.application.CommandBase;
import com.example.meetup.userAccess.base.application.ICommandHandler;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticateCommand extends CommandBase {

	private String login;
	private String password;
    	
    @Override
    public Class<? extends ICommandHandler> getHandlerType() {
        return AuthenticateCommandHandler.class;
    }

}
