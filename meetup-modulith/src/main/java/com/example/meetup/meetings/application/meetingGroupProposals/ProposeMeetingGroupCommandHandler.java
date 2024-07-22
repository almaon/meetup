package com.example.meetup.meetings.application.meetingGroupProposals;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.example.meetup.meetings.base.application.ISyncCommandHandler;
import com.example.meetup.meetings.base.domain.IAggregateStore;
import com.example.meetup.meetings.domain.MeetingGroupLocation;
import com.example.meetup.meetings.domain.meetingGroupProposal.MeetingGroupProposal;
import com.example.meetup.meetings.infrastructure.MeetingContext;

import lombok.RequiredArgsConstructor;
    
@Component
@RequiredArgsConstructor
public class ProposeMeetingGroupCommandHandler implements ISyncCommandHandler<ProposeMeetingGroupCommand, UUID> {
	
	private final IAggregateStore aggregateStore;

	private final MeetingContext context;
	
	
	@Override
	public UUID handle(ProposeMeetingGroupCommand command) {	

		var meetingGroupProposal = new MeetingGroupProposal(
   				command.getName(), 
   				command.getDescription(), 
   				new MeetingGroupLocation(command.getLocationCity(),command.getLocationCountryCode()), 
   				context.principalId());
    	
		aggregateStore.save(meetingGroupProposal);
		
		return meetingGroupProposal.getMeetingGroupProposalId().getValue();
	}	
    	
}
