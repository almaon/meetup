package com.example.meetup.meetings.application.meetings.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.example.meetup.meetings.base.application.IQueryHandler;

import java.util.List;


@Component
@RequiredArgsConstructor
public class GetAllMeetingsMeetingFeesPaymentStatusQueryHandler implements IQueryHandler<GetAllMeetingsMeetingFeesPaymentStatusQuery, List<MeetingsMeetingFeesPaymentStatusView>> {

	private final MeetingsMeetingFeesPaymentStatusRepository meetingsMeetingFeesPaymentStatusRepository;
	
 	@Override
    public List<MeetingsMeetingFeesPaymentStatusView> handle(GetAllMeetingsMeetingFeesPaymentStatusQuery query) {
    	return meetingsMeetingFeesPaymentStatusRepository.findAll();
    }

}
