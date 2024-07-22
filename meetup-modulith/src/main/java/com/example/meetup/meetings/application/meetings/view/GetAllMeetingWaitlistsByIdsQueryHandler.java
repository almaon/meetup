package com.example.meetup.meetings.application.meetings.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.example.meetup.meetings.base.application.IQueryHandler;

import java.util.List;


@Component
@RequiredArgsConstructor
public class GetAllMeetingWaitlistsByIdsQueryHandler implements IQueryHandler<GetAllMeetingWaitlistsByIdsQuery, List<MeetingWaitlistsView>> {

	private final MeetingWaitlistsRepository meetingWaitlistsRepository;
	
 	@Override
    public List<MeetingWaitlistsView> handle(GetAllMeetingWaitlistsByIdsQuery query) {
    	return meetingWaitlistsRepository.findAllById(query.getIds());
    }

}
