package com.example.meetup.meetings.application.meetings.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.example.meetup.meetings.base.application.IQueryHandler;


@Component
@RequiredArgsConstructor
public class GetMeetingsByIdQueryHandler implements IQueryHandler<GetMeetingsByIdQuery, MeetingsView> {

	private final MeetingsRepository meetingsRepository;
	
 	@Override
    public MeetingsView handle(GetMeetingsByIdQuery query) {
    	return meetingsRepository.findByMeetingId(query.getMeetingId());
    }

}
