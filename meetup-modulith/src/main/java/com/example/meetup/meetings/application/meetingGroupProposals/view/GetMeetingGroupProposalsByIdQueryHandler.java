package com.example.meetup.meetings.application.meetingGroupProposals.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.example.meetup.meetings.base.application.IQueryHandler;


@Component
@RequiredArgsConstructor
public class GetMeetingGroupProposalsByIdQueryHandler implements IQueryHandler<GetMeetingGroupProposalsByIdQuery, MeetingGroupProposalsView> {

	private final MeetingGroupProposalsRepository meetingGroupProposalsRepository;
	
 	@Override
    public MeetingGroupProposalsView handle(GetMeetingGroupProposalsByIdQuery query) {
    	return meetingGroupProposalsRepository.findByMeetingGroupProposalId(query.getMeetingGroupProposalId());
    }

}
