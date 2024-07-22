package com.example.meetup.userAccess.application.addAdminUser;


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
public class AddAdminUserCommand extends CommandBase {

	private String password;
	private String lastName;
	private String email;
	private String login;
	private String firstName;
    	
    @Override
    public Class<? extends ICommandHandler> getHandlerType() {
        return AddAdminUserCommandHandler.class;
    }

}
