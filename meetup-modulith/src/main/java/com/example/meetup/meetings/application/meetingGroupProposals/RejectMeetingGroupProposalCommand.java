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
public class RejectMeetingGroupProposalCommand extends CommandBase {

	private UUID meetingGroupProposalId;
	private String rejectReason;
    	
    @Override
    public Class<? extends ICommandHandler> getHandlerType() {
        return RejectMeetingGroupProposalCommandHandler.class;
    }

}
