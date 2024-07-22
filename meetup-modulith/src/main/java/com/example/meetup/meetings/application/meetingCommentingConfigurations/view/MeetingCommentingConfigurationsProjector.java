package com.example.meetup.meetings.application.meetingCommentingConfigurations.view;


import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import com.example.meetup.meetings.base.application.IProjector;
import com.example.meetup.meetings.base.application.IEvent;

import com.example.meetup.meetings.domain.meetingCommentingConfiguration.events.MeetingCommentingConfigurationCreatedEvent;
import com.example.meetup.meetings.domain.meetingCommentingConfiguration.events.MeetingCommentingEnabledEvent;
import com.example.meetup.meetings.domain.meetingCommentingConfiguration.events.MeetingCommentingDisabledEvent;

import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class MeetingCommentingConfigurationsProjector implements IProjector {

	private final MeetingCommentingConfigurationsRepository meetingCommentingConfigurationsRepository;
	


	
	private void when(MeetingCommentingDisabledEvent event) {

		var view = meetingCommentingConfigurationsRepository.findByMeetingId(event.getMeetingId());
		
		view.setIsCommentingEnabled(false);

	}
	private void when(MeetingCommentingConfigurationCreatedEvent event) {

		meetingCommentingConfigurationsRepository.save(
			new MeetingCommentingConfigurationsView(
				event.getMeetingId(),
				event.getIsEnabled(),
				event.getMeetingCommentingConfigurationId()));

	}
	private void when(MeetingCommentingEnabledEvent event) {

		var view = meetingCommentingConfigurationsRepository.findByMeetingId(event.getMeetingId());
		
		view.setIsCommentingEnabled(true);

	}
	
	@Override
//	@Transactional//(propagation = Propagation.REQUIRES_NEW)
	public void project(IEvent event) {
		
		if (event instanceof MeetingCommentingDisabledEvent castedEvent) {
			when(castedEvent);
			return;
		}
		if (event instanceof MeetingCommentingConfigurationCreatedEvent castedEvent) {
			when(castedEvent);
			return;
		}
		if (event instanceof MeetingCommentingEnabledEvent castedEvent) {
			when(castedEvent);
			return;
		}
			
	}
	
}
