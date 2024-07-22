package com.example.meetup.meetings.application.meetingCommentingConfigurations.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.example.meetup.meetings.base.application.IQueryHandler;

import java.util.List;


@Component
@RequiredArgsConstructor
public class GetAllMeetingCommentingConfigurationsQueryHandler implements IQueryHandler<GetAllMeetingCommentingConfigurationsQuery, List<MeetingCommentingConfigurationsView>> {

	private final MeetingCommentingConfigurationsRepository meetingCommentingConfigurationsRepository;
	
 	@Override
    public List<MeetingCommentingConfigurationsView> handle(GetAllMeetingCommentingConfigurationsQuery query) {
    	return meetingCommentingConfigurationsRepository.findAll();
    }

}
