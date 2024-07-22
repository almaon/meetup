package com.example.meetup.meetings.application.meetingGroupProposals.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.example.meetup.meetings.base.application.IQueryHandler;

import java.util.List;


@Component
@RequiredArgsConstructor
public class GetAllMeetingGroupProposalsQueryHandler implements IQueryHandler<GetAllMeetingGroupProposalsQuery, List<MeetingGroupProposalsView>> {

	private final MeetingGroupProposalsRepository meetingGroupProposalsRepository;
	
 	@Override
    public List<MeetingGroupProposalsView> handle(GetAllMeetingGroupProposalsQuery query) {
    	return meetingGroupProposalsRepository.findAll();
    }

}
