package com.example.meetup.payments.application.meetingFees;


import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.NoArgsConstructor;

import com.example.meetup.payments.base.application.CommandBase;
import com.example.meetup.payments.base.application.ICommandHandler;



@Getter
@Setter
@NoArgsConstructor
public class ExecuteMeetingFeePaymentCommand extends CommandBase {

    	
    @Override
    public Class<? extends ICommandHandler> getHandlerType() {
        return ExecuteMeetingFeePaymentCommandHandler.class;
    }

}
