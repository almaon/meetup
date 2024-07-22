package com.example.meetup.userAccess.application.confirmUserRegistration;


import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.NoArgsConstructor;

import com.example.meetup.userAccess.base.application.CommandBase;
import com.example.meetup.userAccess.base.application.ICommandHandler;

import jakarta.validation.constraints.NotBlank;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmUserRegistrationCommand extends CommandBase {

	@NotBlank
	private String confirmLink;
    	
    @Override
    public Class<? extends ICommandHandler> getHandlerType() {
        return ConfirmUserRegistrationCommandHandler.class;
    }

}
