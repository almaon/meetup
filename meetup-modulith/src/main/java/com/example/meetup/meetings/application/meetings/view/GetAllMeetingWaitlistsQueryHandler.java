package com.example.meetup.meetings.application.meetings.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.example.meetup.meetings.base.application.IQueryHandler;

import java.util.List;


@Component
@RequiredArgsConstructor
public class GetAllMeetingWaitlistsQueryHandler implements IQueryHandler<GetAllMeetingWaitlistsQuery, List<MeetingWaitlistsView>> {

	private final MeetingWaitlistsRepository meetingWaitlistsRepository;
	
 	@Override
    public List<MeetingWaitlistsView> handle(GetAllMeetingWaitlistsQuery query) {
    	return meetingWaitlistsRepository.findAll();
    }

}
