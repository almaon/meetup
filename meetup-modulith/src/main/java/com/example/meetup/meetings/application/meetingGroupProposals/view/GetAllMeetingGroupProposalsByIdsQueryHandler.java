package com.example.meetup.meetings.application.meetingGroupProposals.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.example.meetup.meetings.base.application.IQueryHandler;

import java.util.List;


@Component
@RequiredArgsConstructor
public class GetAllMeetingGroupProposalsByIdsQueryHandler implements IQueryHandler<GetAllMeetingGroupProposalsByIdsQuery, List<MeetingGroupProposalsView>> {

	private final MeetingGroupProposalsRepository meetingGroupProposalsRepository;
	
 	@Override
    public List<MeetingGroupProposalsView> handle(GetAllMeetingGroupProposalsByIdsQuery query) {
    	return meetingGroupProposalsRepository.findAllById(query.getIds());
    }

}
