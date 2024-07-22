package com.example.meetup.userAccess.application.createAdmin;

import com.example.meetup.userAccess.base.application.CommandBase;
import com.example.meetup.userAccess.base.application.ICommandHandler;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserAccessCreateAdminDemoCommand extends CommandBase {

	@NotBlank
	private String login;
	@NotBlank
	private String password;
	@NotBlank
	private String firstName;
	@NotBlank
	private String email;
	@NotBlank
	private String lastName;
    	
    @Override
    public Class<? extends ICommandHandler> getHandlerType() {
        return UserAccessCreateAdminDemoCommandHandler.class;
    }

}
