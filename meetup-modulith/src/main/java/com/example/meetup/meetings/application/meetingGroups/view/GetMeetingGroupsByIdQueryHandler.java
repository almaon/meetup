package com.example.meetup.meetings.application.meetingGroups.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.example.meetup.meetings.base.application.IQueryHandler;

@Component
@RequiredArgsConstructor
public class GetMeetingGroupsByIdQueryHandler implements IQueryHandler<GetMeetingGroupsByIdQuery, MeetingGroupsView> {

	private final MeetingGroupsRepository meetingGroupsRepository;

	@Override
	public MeetingGroupsView handle(GetMeetingGroupsByIdQuery query) {
		return meetingGroupsRepository.findByMeetingGroupId(query.getMeetingGroupId());
	}

}
