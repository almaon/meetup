package com.example.meetup.userAccess.application.sendUserRegistrationConfirmation;


import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.NoArgsConstructor;

import com.example.meetup.userAccess.base.application.CommandBase;
import com.example.meetup.userAccess.base.application.ICommandHandler;

import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SendUserRegistrationConfirmationCommand extends CommandBase {

	private UUID userRegistrationId;
	private String confirmLink;
	private String email;
    	
    @Override
    public Class<? extends ICommandHandler> getHandlerType() {
        return SendUserRegistrationConfirmationCommandHandler.class;
    }

}
