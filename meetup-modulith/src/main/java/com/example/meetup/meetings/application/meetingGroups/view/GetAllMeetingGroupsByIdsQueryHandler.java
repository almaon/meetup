package com.example.meetup.meetings.application.meetingGroups.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.example.meetup.meetings.base.application.IQueryHandler;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetAllMeetingGroupsByIdsQueryHandler
		implements IQueryHandler<GetAllMeetingGroupsByIdsQuery, List<MeetingGroupsView>> {

	private final MeetingGroupsRepository meetingGroupsRepository;

	@Override
	public List<MeetingGroupsView> handle(GetAllMeetingGroupsByIdsQuery query) {
		return meetingGroupsRepository.findAllById(query.getIds());
	}

}
