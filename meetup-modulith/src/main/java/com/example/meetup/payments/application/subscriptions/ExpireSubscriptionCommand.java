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
public class ExpireSubscriptionCommand extends CommandBase {

	private UUID subscriptionId;
    	
    @Override
    public Class<? extends ICommandHandler> getHandlerType() {
        return ExpireSubscriptionCommandHandler.class;
    }

}
