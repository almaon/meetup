package com.example.meetup.userAccess.application.sendRegistrationConfirmationLink;


import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.NoArgsConstructor;

import com.example.meetup.userAccess.base.application.CommandBase;
import com.example.meetup.userAccess.base.application.ICommandHandler;



@Getter
@Setter
@NoArgsConstructor
public class SendRegistrationConfirmationLinkCommand extends CommandBase {

    	
    @Override
    public Class<? extends ICommandHandler> getHandlerType() {
        return SendRegistrationConfirmationLinkCommandHandler.class;
    }

}
