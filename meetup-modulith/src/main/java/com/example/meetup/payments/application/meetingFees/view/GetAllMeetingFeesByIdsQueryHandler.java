package com.example.meetup.payments.application.meetingFees.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.example.meetup.payments.base.application.IQueryHandler;

import java.util.List;


@Component
@RequiredArgsConstructor
public class GetAllMeetingFeesByIdsQueryHandler implements IQueryHandler<GetAllMeetingFeesByIdsQuery, List<MeetingFeesView>> {

	private final MeetingFeesRepository meetingFeesRepository;
	
 	@Override
    public List<MeetingFeesView> handle(GetAllMeetingFeesByIdsQuery query) {
    	return meetingFeesRepository.findAllById(query.getIds());
    }

}
