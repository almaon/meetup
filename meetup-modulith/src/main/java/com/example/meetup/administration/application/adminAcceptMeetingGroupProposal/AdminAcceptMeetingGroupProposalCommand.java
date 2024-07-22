package com.example.meetup.administration.application.adminAcceptMeetingGroupProposal;


import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.NoArgsConstructor;

import com.example.meetup.administration.base.application.CommandBase;
import com.example.meetup.administration.base.application.ICommandHandler;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminAcceptMeetingGroupProposalCommand extends CommandBase {

	@NotNull
	private UUID meetingGroupProposalId;
    	
    @Override
    public Class<? extends ICommandHandler> getHandlerType() {
        return AdminAcceptMeetingGroupProposalCommandHandler.class;
    }

}
