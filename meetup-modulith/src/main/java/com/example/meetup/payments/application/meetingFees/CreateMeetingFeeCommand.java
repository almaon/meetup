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
public class CreateMeetingFeeCommand extends CommandBase {

	private double value;
	private UUID meetingId;
	private UUID payerId;
	private String currency;
    	
    @Override
    public Class<? extends ICommandHandler> getHandlerType() {
        return CreateMeetingFeeCommandHandler.class;
    }

}
