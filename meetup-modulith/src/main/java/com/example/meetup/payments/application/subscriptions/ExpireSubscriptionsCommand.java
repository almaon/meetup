package com.example.meetup.payments.application.subscriptions;


import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.NoArgsConstructor;

import com.example.meetup.payments.base.application.CommandBase;
import com.example.meetup.payments.base.application.ICommandHandler;



@Getter
@Setter
@NoArgsConstructor
public class ExpireSubscriptionsCommand extends CommandBase {

    	
    @Override
    public Class<? extends ICommandHandler> getHandlerType() {
        return ExpireSubscriptionsCommandHandler.class;
    }

}
