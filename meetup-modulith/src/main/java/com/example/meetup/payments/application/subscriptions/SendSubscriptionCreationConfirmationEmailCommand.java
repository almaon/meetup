package com.example.meetup.payments.application.subscriptions;


import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.NoArgsConstructor;

import com.example.meetup.payments.base.application.CommandBase;
import com.example.meetup.payments.base.application.ICommandHandler;

import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SendSubscriptionCreationConfirmationEmailCommand extends CommandBase {

	private UUID subscriptionId;
	private String email;
    	
    @Override
    public Class<? extends ICommandHandler> getHandlerType() {
        return SendSubscriptionCreationConfirmationEmailCommandHandler.class;
    }

}
