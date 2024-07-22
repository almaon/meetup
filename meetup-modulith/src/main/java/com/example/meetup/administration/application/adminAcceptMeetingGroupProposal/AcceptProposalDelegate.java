package com.example.meetup.administration.application.adminAcceptMeetingGroupProposal;

import java.util.UUID;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import com.example.meetup.administration.base.domain.IAggregateStore;
import com.example.meetup.administration.domain.meetingGroupProposals.MeetingGroupProposal;
import com.example.meetup.administration.domain.meetingGroupProposals.MeetingGroupProposalId;
import com.example.meetup.administration.infrastructure.AdministrationContext;
import com.example.meetup.administration.infrastructure.exception.NotFoundException;

import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
public class AcceptProposalDelegate implements JavaDelegate {

	private final IAggregateStore aggregateStore;

	private final AdministrationContext context;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

		var meetingGroupProposal = (MeetingGroupProposal) aggregateStore.load(
				new MeetingGroupProposalId(UUID.fromString(delegateExecution.getBusinessKey()))
					, MeetingGroupProposal.class)
				.orElseThrow(() -> new NotFoundException("Meeting Group Proposal not found"));
    	
   		meetingGroupProposal.accept(context.principalId());
		
		aggregateStore.save(meetingGroupProposal);
    }
}
