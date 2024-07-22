package com.example.meetup.userAccess.application.registerNewUser;

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
public class RegisterNewUserCommand extends CommandBase {

	@NotBlank
	private String login;
	@NotBlank
	private String password;
	@NotBlank
	private String lastName;
	@NotBlank
	private String firstName;
	@NotBlank
	private String email;

	@Override
	public Class<? extends ICommandHandler> getHandlerType() {
		return RegisterNewUserCommandHandler.class;
	}

}
