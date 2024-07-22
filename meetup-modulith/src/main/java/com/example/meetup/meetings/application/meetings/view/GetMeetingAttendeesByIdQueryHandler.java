package com.example.meetup.meetings.application.meetings.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.example.meetup.meetings.base.application.IQueryHandler;


@Component
@RequiredArgsConstructor
public class GetMeetingAttendeesByIdQueryHandler implements IQueryHandler<GetMeetingAttendeesByIdQuery, MeetingAttendeesView> {

	private final MeetingAttendeesRepository meetingAttendeesRepository;
	
 	@Override
    public MeetingAttendeesView handle(GetMeetingAttendeesByIdQuery query) {
    	return meetingAttendeesRepository.findByMeetingId(query.getMeetingId());
    }

}
