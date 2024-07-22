package com.example.meetup.payments.application.meetingFees;


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
public class MarkMeetingFeePaymentAsPaidCommand extends CommandBase {

	private UUID meetingFeePaymentId;
    	
    @Override
    public Class<? extends ICommandHandler> getHandlerType() {
        return MarkMeetingFeePaymentAsPaidCommandHandler.class;
    }

}
