package com.example.meetup.administration.application.adminMeetingGroupProposals.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.example.meetup.administration.base.application.IQueryHandler;

import java.util.List;


@Component
@RequiredArgsConstructor
public class GetAllAdminMeetingGroupProposalsQueryHandler implements IQueryHandler<GetAllAdminMeetingGroupProposalsQuery, List<AdminMeetingGroupProposalsView>> {

	private final AdminMeetingGroupProposalsRepository adminMeetingGroupProposalsRepository;
	
 	@Override
    public List<AdminMeetingGroupProposalsView> handle(GetAllAdminMeetingGroupProposalsQuery query) {
    	return adminMeetingGroupProposalsRepository.findAll();
    }

}
