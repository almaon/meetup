package com.example.meetup.meetings.application.meetingGroups.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.example.meetup.meetings.base.application.IQueryHandler;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetAllMemberMeetingGroupsByIdsQueryHandler
		implements IQueryHandler<GetAllMemberMeetingGroupsByIdsQuery, List<MemberMeetingGroupsView>> {

	private final MemberMeetingGroupsRepository memberMeetingGroupsRepository;

	@Override
	public List<MemberMeetingGroupsView> handle(GetAllMemberMeetingGroupsByIdsQuery query) {
		return memberMeetingGroupsRepository.findAllById(query.getIds());
	}

}
