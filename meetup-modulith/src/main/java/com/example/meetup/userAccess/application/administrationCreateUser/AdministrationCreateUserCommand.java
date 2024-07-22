package com.example.meetup.userAccess.application.administrationCreateUser;

import java.time.LocalDateTime;

import com.example.meetup.userAccess.base.application.CommandBase;
import com.example.meetup.userAccess.base.application.ICommandHandler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdministrationCreateUserCommand extends CommandBase {

	private LocalDateTime registerDate;
	private String login;
	private String lastName;
	private String firstName;
	private String email;
    	
    @Override
    public Class<? extends ICommandHandler> getHandlerType() {
        return AdministrationCreateUserCommandHandler.class;
    }

}
