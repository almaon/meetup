package com.example.meetup.administration.application.createAdmin;


import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.NoArgsConstructor;

import com.example.meetup.administration.base.application.CommandBase;
import com.example.meetup.administration.base.application.ICommandHandler;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateAdminCommand extends CommandBase {

	private UUID adminId;
	private String firstName;
	private String lastName;
	private String email;
	private String login;
	private LocalDateTime registerDate;
    	
    @Override
    public Class<? extends ICommandHandler> getHandlerType() {
        return CreateAdminCommandHandler.class;
    }

}
