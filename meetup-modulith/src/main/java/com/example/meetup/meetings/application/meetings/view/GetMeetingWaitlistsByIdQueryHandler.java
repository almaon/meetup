package com.example.meetup.meetings.application.meetings.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.example.meetup.meetings.base.application.IQueryHandler;


@Component
@RequiredArgsConstructor
public class GetMeetingWaitlistsByIdQueryHandler implements IQueryHandler<GetMeetingWaitlistsByIdQuery, MeetingWaitlistsView> {

	private final MeetingWaitlistsRepository meetingWaitlistsRepository;
	
 	@Override
    public MeetingWaitlistsView handle(GetMeetingWaitlistsByIdQuery query) {
    	return meetingWaitlistsRepository.findByMeetingId(query.getMeetingId());
    }

}
