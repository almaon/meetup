package com.example.meetup.administration.application.requestMeetingGroupProposalVerification;


import org.springframework.stereotype.Component;

import com.example.meetup.administration.base.application.IAsyncCommandHandler;
import com.example.meetup.administration.base.domain.IAggregateStore;
import com.example.meetup.administration.domain.administrator.AdministratorId;
import com.example.meetup.administration.domain.meetingGroupProposals.MeetingGroupLocation;
import com.example.meetup.administration.domain.meetingGroupProposals.MeetingGroupProposal;
import com.example.meetup.administration.domain.meetingGroupProposals.MeetingGroupProposalId;
import com.example.meetup.administration.domain.members.MemberId;

import lombok.RequiredArgsConstructor;

    
@Component
@RequiredArgsConstructor
public class RequestMeetingGroupProposalVerificationCommandHandler implements IAsyncCommandHandler<RequestMeetingGroupProposalVerificationCommand> {

	private final IAggregateStore aggregateStore;


	@Override
	public void handle(RequestMeetingGroupProposalVerificationCommand command) {

   		var meetingGroupProposal = new MeetingGroupProposal(
   			new MeetingGroupProposalId(command.getMeetingGroupProposalId()),
   			command.getName(),
   			command.getDescription(),
   			new MeetingGroupLocation(
   					command.getLocationCity(), 
   					command.getLocationCountryCode()),
   			new MemberId(command.getProposalUserId()),
   			command.getProposalDate());
    		
		aggregateStore.save(meetingGroupProposal);

	}	
    	
}
