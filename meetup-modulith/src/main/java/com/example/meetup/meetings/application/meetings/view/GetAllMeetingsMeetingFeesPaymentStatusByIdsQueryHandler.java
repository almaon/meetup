package com.example.meetup.meetings.application.meetings.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.example.meetup.meetings.base.application.IQueryHandler;

import java.util.List;


@Component
@RequiredArgsConstructor
public class GetAllMeetingsMeetingFeesPaymentStatusByIdsQueryHandler implements IQueryHandler<GetAllMeetingsMeetingFeesPaymentStatusByIdsQuery, List<MeetingsMeetingFeesPaymentStatusView>> {

	private final MeetingsMeetingFeesPaymentStatusRepository meetingsMeetingFeesPaymentStatusRepository;
	
 	@Override
    public List<MeetingsMeetingFeesPaymentStatusView> handle(GetAllMeetingsMeetingFeesPaymentStatusByIdsQuery query) {
    	return meetingsMeetingFeesPaymentStatusRepository.findAllById(query.getIds());
    }

}
