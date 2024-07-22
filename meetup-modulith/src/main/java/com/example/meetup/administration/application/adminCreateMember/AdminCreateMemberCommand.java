package com.example.meetup.administration.application.adminCreateMember;


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
public class AdminCreateMemberCommand extends CommandBase {

	private UUID memberId;
	private String login;
	private String email;
	private String firstName;
	private String lastName;
	private LocalDateTime registerDate;
    	
    @Override
    public Class<? extends ICommandHandler> getHandlerType() {
        return AdminCreateMemberCommandHandler.class;
    }

}
