package com.example.meetup.meetings.application.meetings.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.example.meetup.meetings.base.application.IQueryHandler;

import java.util.List;


@Component
@RequiredArgsConstructor
public class GetAllMeetingsQueryHandler implements IQueryHandler<GetAllMeetingsQuery, List<MeetingsView>> {

	private final MeetingsRepository meetingsRepository;
	
 	@Override
    public List<MeetingsView> handle(GetAllMeetingsQuery query) {
    	return meetingsRepository.findAll();
    }

}
