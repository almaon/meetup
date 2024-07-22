package com.example.meetup.meetings.application.meetingGroupProposals;


import com.example.meetup.meetings.base.application.IAsyncCommandHandler;
import com.example.meetup.meetings.base.domain.IAggregateStore;



import com.example.meetup.meetings.domain.meetingGroupProposal.MeetingGroupProposalId;
import com.example.meetup.meetings.domain.meetingGroupProposal.MeetingGroupProposal;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

    
@Component
@RequiredArgsConstructor
public class RejectMeetingGroupProposalCommandHandler implements IAsyncCommandHandler<RejectMeetingGroupProposalCommand> {

	private final IAggregateStore aggregateStore;


	@Override
	public void handle(RejectMeetingGroupProposalCommand command) {

		MeetingGroupProposal meetingGroupProposal = aggregateStore.load(new MeetingGroupProposalId(command.getMeetingGroupProposalId()), MeetingGroupProposal.class);
    	
		meetingGroupProposal.reject(command.getRejectReason());
		
		aggregateStore.save(meetingGroupProposal);

	}	
    	
}
