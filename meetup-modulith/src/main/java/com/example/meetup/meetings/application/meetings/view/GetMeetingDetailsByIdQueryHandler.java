package com.example.meetup.meetings.application.meetings.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.example.meetup.meetings.base.application.IQueryHandler;


@Component
@RequiredArgsConstructor
public class GetMeetingDetailsByIdQueryHandler implements IQueryHandler<GetMeetingDetailsByIdQuery, MeetingDetailsView> {

	private final MeetingDetailsRepository meetingDetailsRepository;
	
 	@Override
    public MeetingDetailsView handle(GetMeetingDetailsByIdQuery query) {
    	return meetingDetailsRepository.findByMeetingId(query.getMeetingId());
    }

}
