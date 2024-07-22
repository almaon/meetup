package com.example.meetup.meetings.application.meetings.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.example.meetup.meetings.base.application.IQueryHandler;

import java.util.List;


@Component
@RequiredArgsConstructor
public class GetAllMeetingDetailsQueryHandler implements IQueryHandler<GetAllMeetingDetailsQuery, List<MeetingDetailsView>> {

	private final MeetingDetailsRepository meetingDetailsRepository;
	
 	@Override
    public List<MeetingDetailsView> handle(GetAllMeetingDetailsQuery query) {
    	return meetingDetailsRepository.findAll();
    }

}
