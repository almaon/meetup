package com.example.meetup.meetings.application.meetingCommentingConfigurations.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.example.meetup.meetings.base.application.IQueryHandler;


@Component
@RequiredArgsConstructor
public class GetMeetingCommentingConfigurationsByIdQueryHandler implements IQueryHandler<GetMeetingCommentingConfigurationsByIdQuery, MeetingCommentingConfigurationsView> {

	private final MeetingCommentingConfigurationsRepository meetingCommentingConfigurationsRepository;
	
 	@Override
    public MeetingCommentingConfigurationsView handle(GetMeetingCommentingConfigurationsByIdQuery query) {
    	return meetingCommentingConfigurationsRepository.findByMeetingId(query.getMeetingId());
    }

}
