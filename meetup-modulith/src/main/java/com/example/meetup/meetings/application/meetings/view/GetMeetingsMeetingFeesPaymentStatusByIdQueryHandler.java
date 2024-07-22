package com.example.meetup.meetings.application.meetings.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.example.meetup.meetings.base.application.IQueryHandler;


@Component
@RequiredArgsConstructor
public class GetMeetingsMeetingFeesPaymentStatusByIdQueryHandler implements IQueryHandler<GetMeetingsMeetingFeesPaymentStatusByIdQuery, MeetingsMeetingFeesPaymentStatusView> {

	private final MeetingsMeetingFeesPaymentStatusRepository meetingsMeetingFeesPaymentStatusRepository;
	
 	@Override
    public MeetingsMeetingFeesPaymentStatusView handle(GetMeetingsMeetingFeesPaymentStatusByIdQuery query) {
    	return meetingsMeetingFeesPaymentStatusRepository.findByMeetingId(query.getMeetingId());
    }

}
