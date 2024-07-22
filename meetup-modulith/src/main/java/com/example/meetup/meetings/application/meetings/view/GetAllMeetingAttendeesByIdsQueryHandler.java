package com.example.meetup.meetings.application.meetings.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.example.meetup.meetings.base.application.IQueryHandler;

import java.util.List;


@Component
@RequiredArgsConstructor
public class GetAllMeetingAttendeesByIdsQueryHandler implements IQueryHandler<GetAllMeetingAttendeesByIdsQuery, List<MeetingAttendeesView>> {

	private final MeetingAttendeesRepository meetingAttendeesRepository;
	
 	@Override
    public List<MeetingAttendeesView> handle(GetAllMeetingAttendeesByIdsQuery query) {
    	return meetingAttendeesRepository.findAllById(query.getIds());
    }

}
