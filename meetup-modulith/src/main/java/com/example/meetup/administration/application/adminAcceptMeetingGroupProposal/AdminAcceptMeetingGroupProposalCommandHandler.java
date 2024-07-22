package com.example.meetup.administration.application.adminAcceptMeetingGroupProposal;

import org.camunda.bpm.engine.RuntimeService;
import org.springframework.stereotype.Component;

import com.example.meetup.administration.base.application.IAsyncCommandHandler;
import com.example.meetup.administration.base.domain.IAggregateStore;
import com.example.meetup.administration.domain.meetingGroupProposals.MeetingGroupProposal;
import com.example.meetup.administration.domain.meetingGroupProposals.MeetingGroupProposalId;
import com.example.meetup.administration.infrastructure.AdministrationContext;
import com.example.meetup.administration.infrastructure.exception.NotFoundException;
import com.example.meetup.userAccess.application.registerNewUser.RegisterNewUserCommandHandler.BusinessKeyHolder;

import lombok.RequiredArgsConstructor;
    
@Component
@RequiredArgsConstructor
public class AdminAcceptMeetingGroupProposalCommandHandler implements IAsyncCommandHandler<AdminAcceptMeetingGroupProposalCommand> {

	private final IAggregateStore aggregateStore;

	private final AdministrationContext context;

	private final RuntimeService runtimeService;
	
	@Override
	public void handle(AdminAcceptMeetingGroupProposalCommand command) {
		try {
			
			runtimeService.createMessageCorrelation("proposal-desicion")
				.processInstanceBusinessKey(command.getMeetingGroupProposalId().toString())
				.setVariable("decision", "accept")
				.correlate();	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
    	
}