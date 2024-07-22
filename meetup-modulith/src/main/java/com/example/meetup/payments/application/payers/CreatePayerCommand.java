package com.example.meetup.payments.application.payers;


import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.NoArgsConstructor;

import com.example.meetup.payments.base.application.CommandBase;
import com.example.meetup.payments.base.application.ICommandHandler;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreatePayerCommand extends CommandBase {

	private UUID payerId;
	private String lastName;
	private String email;
	private String login;
	private String firstName;
	private LocalDateTime registerDate;
    	
    @Override
    public Class<? extends ICommandHandler> getHandlerType() {
        return CreatePayerCommandHandler.class;
    }

}
