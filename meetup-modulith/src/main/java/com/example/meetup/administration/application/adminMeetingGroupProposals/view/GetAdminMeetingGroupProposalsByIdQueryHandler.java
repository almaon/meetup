package com.example.meetup.administration.application.adminMeetingGroupProposals.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.example.meetup.administration.base.application.IQueryHandler;


@Component
@RequiredArgsConstructor
public class GetAdminMeetingGroupProposalsByIdQueryHandler implements IQueryHandler<GetAdminMeetingGroupProposalsByIdQuery, AdminMeetingGroupProposalsView> {

	private final AdminMeetingGroupProposalsRepository adminMeetingGroupProposalsRepository;
	
 	@Override
    public AdminMeetingGroupProposalsView handle(GetAdminMeetingGroupProposalsByIdQuery query) {
    	return adminMeetingGroupProposalsRepository.findByMeetingGroupProposalId(query.getMeetingGroupProposalId());
    }

}
