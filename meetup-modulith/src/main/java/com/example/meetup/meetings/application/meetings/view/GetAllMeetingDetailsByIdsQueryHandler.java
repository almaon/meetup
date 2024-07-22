package com.example.meetup.meetings.application.meetings.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.example.meetup.meetings.base.application.IQueryHandler;

import java.util.List;


@Component
@RequiredArgsConstructor
public class GetAllMeetingDetailsByIdsQueryHandler implements IQueryHandler<GetAllMeetingDetailsByIdsQuery, List<MeetingDetailsView>> {

	private final MeetingDetailsRepository meetingDetailsRepository;
	
 	@Override
    public List<MeetingDetailsView> handle(GetAllMeetingDetailsByIdsQuery query) {
    	return meetingDetailsRepository.findAllById(query.getIds());
    }

}
