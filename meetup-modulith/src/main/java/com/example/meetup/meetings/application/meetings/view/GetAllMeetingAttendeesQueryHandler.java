package com.example.meetup.meetings.application.meetings.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.example.meetup.meetings.base.application.IQueryHandler;

import java.util.List;


@Component
@RequiredArgsConstructor
public class GetAllMeetingAttendeesQueryHandler implements IQueryHandler<GetAllMeetingAttendeesQuery, List<MeetingAttendeesView>> {

	private final MeetingAttendeesRepository meetingAttendeesRepository;
	
 	@Override
    public List<MeetingAttendeesView> handle(GetAllMeetingAttendeesQuery query) {
    	return meetingAttendeesRepository.findAll();
    }

}
