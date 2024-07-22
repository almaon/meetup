package com.example.meetup.administration.application.adminMeetingGroupProposals.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.example.meetup.administration.base.application.IQueryHandler;

import java.util.List;


@Component
@RequiredArgsConstructor
public class GetAllAdminMeetingGroupProposalsByIdsQueryHandler implements IQueryHandler<GetAllAdminMeetingGroupProposalsByIdsQuery, List<AdminMeetingGroupProposalsView>> {

	private final AdminMeetingGroupProposalsRepository adminMeetingGroupProposalsRepository;
	
 	@Override
    public List<AdminMeetingGroupProposalsView> handle(GetAllAdminMeetingGroupProposalsByIdsQuery query) {
    	return adminMeetingGroupProposalsRepository.findAllById(query.getIds());
    }

}
