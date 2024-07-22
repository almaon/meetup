package com.example.meetup.meetings.application.meetingGroups.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.example.meetup.meetings.base.application.IQueryHandler;

@Component
@RequiredArgsConstructor
public class GetMemberMeetingGroupsByIdQueryHandler
		implements IQueryHandler<GetMemberMeetingGroupsByIdQuery, MemberMeetingGroupsView> {

	private final MemberMeetingGroupsRepository memberMeetingGroupsRepository;

	@Override
	public MemberMeetingGroupsView handle(GetMemberMeetingGroupsByIdQuery query) {
		return memberMeetingGroupsRepository.findByMemberId(query.getMemberId());
	}

}
