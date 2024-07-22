package com.example.meetup.meetings.application.memberSubscription;


import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.NoArgsConstructor;

import com.example.meetup.meetings.base.application.CommandBase;
import com.example.meetup.meetings.base.application.ICommandHandler;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChangeSubscriptionExpirationDateForMemberCommand extends CommandBase {

	private UUID memberId;
	private LocalDateTime expirationDate;
    	
    @Override
    public Class<? extends ICommandHandler> getHandlerType() {
        return ChangeSubscriptionExpirationDateForMemberCommandHandler.class;
    }

}
