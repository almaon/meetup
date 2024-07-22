package com.example.meetup.meetings.application.meetings.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.example.meetup.meetings.base.application.IQueryHandler;

import java.util.List;


@Component
@RequiredArgsConstructor
public class GetAllMeetingsByIdsQueryHandler implements IQueryHandler<GetAllMeetingsByIdsQuery, List<MeetingsView>> {

	private final MeetingsRepository meetingsRepository;
	
 	@Override
    public List<MeetingsView> handle(GetAllMeetingsByIdsQuery query) {
    	return meetingsRepository.findAllById(query.getIds());
    }

}
