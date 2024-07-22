package com.example.meetup.meetings.application.meetingGroupProposals;


import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.NoArgsConstructor;

import com.example.meetup.meetings.base.application.CommandBase;
import com.example.meetup.meetings.base.application.ICommandHandler;

import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AcceptMeetingGroupProposalCommand extends CommandBase {

	private UUID meetingGroupProposalId;
    	
    @Override
    public Class<? extends ICommandHandler> getHandlerType() {
        return AcceptMeetingGroupProposalCommandHandler.class;
    }

}
