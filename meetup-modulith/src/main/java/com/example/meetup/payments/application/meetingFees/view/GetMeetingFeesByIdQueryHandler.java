package com.example.meetup.payments.application.meetingFees.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.example.meetup.payments.base.application.IQueryHandler;


@Component
@RequiredArgsConstructor
public class GetMeetingFeesByIdQueryHandler implements IQueryHandler<GetMeetingFeesByIdQuery, MeetingFeesView> {

	private final MeetingFeesRepository meetingFeesRepository;
	
 	@Override
    public MeetingFeesView handle(GetMeetingFeesByIdQuery query) {
    	return meetingFeesRepository.findByMeetingFeeId(query.getMeetingFeeId());
    }

}
