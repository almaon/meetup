package com.example.meetup.administration.application.requestMeetingGroupProposalVerification;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Component;

import com.example.meetup.administration.base.application.IEvent;
import com.example.meetup.administration.base.application.IEventHandler;
import com.example.meetup.administration.domain.meetingGroupProposals.events.MeetingGroupProposalVerificationRequestedEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MeetingGroupProposalVerificationRequestedEventListener
		implements IEventHandler<MeetingGroupProposalVerificationRequestedEvent> {
	
	private final RuntimeService runtimeService;

	@Override
	public Class<? extends IEvent> registeredFor() {
		return MeetingGroupProposalVerificationRequestedEvent.class;
	}

	@Override
	public void handle(MeetingGroupProposalVerificationRequestedEvent event) {

		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(
				"proposal", 
				event.getMeetingGroupProposalId().toString());
	}

}