package com.example.meetup.meetings.application.meetingGroups.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.example.meetup.meetings.base.application.IQueryHandler;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetAllMemberMeetingGroupsQueryHandler
		implements IQueryHandler<GetAllMemberMeetingGroupsQuery, List<MemberMeetingGroupsView>> {

	private final MemberMeetingGroupsRepository memberMeetingGroupsRepository;

	@Override
	public List<MemberMeetingGroupsView> handle(GetAllMemberMeetingGroupsQuery query) {
		return memberMeetingGroupsRepository.findAll();
	}

}
