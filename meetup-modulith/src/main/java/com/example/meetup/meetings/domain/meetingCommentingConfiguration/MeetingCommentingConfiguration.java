package com.example.meetup.meetings.domain.meetingCommentingConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.example.meetup.meetings.base.domain.Aggregate;
import com.example.meetup.meetings.base.domain.Entity;
import com.example.meetup.meetings.base.domain.IDomainEvent;
import com.example.meetup.meetings.domain.meeting.MeetingId;
import com.example.meetup.meetings.domain.meetingCommentingConfiguration.events.MeetingCommentingConfigurationCreatedEvent;
import com.example.meetup.meetings.domain.meetingCommentingConfiguration.events.MeetingCommentingDisabledEvent;
import com.example.meetup.meetings.domain.meetingCommentingConfiguration.events.MeetingCommentingEnabledEvent;
import com.example.meetup.meetings.domain.meetingCommentingConfiguration.rules.MeetingCommentingCanBeDisabledOnlyByGroupOrganizerRule;
import com.example.meetup.meetings.domain.meetingCommentingConfiguration.rules.MeetingCommentingCanBeEnabledOnlyByGroupOrganizerRule;
import com.example.meetup.meetings.domain.meetingGroup.MeetingGroup;
import com.example.meetup.meetings.domain.member.MemberId;

import lombok.Getter;
import lombok.Setter;

@Getter
public class MeetingCommentingConfiguration extends Aggregate {

	// business id
	@Setter // for testing
	protected MeetingCommentingConfigurationId meetingCommentingConfigurationId;

	public String getStreamId() {
		return "Meetings-MeetingCommentingConfiguration-" + meetingCommentingConfigurationId.getValue();
	}

	protected MeetingId meetingId;
	protected Boolean isCommentingEnabled;

	public MeetingCommentingConfiguration() {
	}

	public MeetingCommentingConfiguration(MeetingId meetingId) {
		
		var meetingCommentingConfigurationCreated = new MeetingCommentingConfigurationCreatedEvent(
				meetingId.getValue(),
				false,
				UUID.randomUUID());

		apply(meetingCommentingConfigurationCreated);
		addDomainEvent(meetingCommentingConfigurationCreated);
	}

	public void enableCommenting(MemberId enablingMemberId, MeetingGroup meetingGroup) {
        checkRule(new MeetingCommentingCanBeEnabledOnlyByGroupOrganizerRule(enablingMemberId, meetingGroup));

		if (!isCommentingEnabled) {			
			var meetingCommentingEnabled = new MeetingCommentingEnabledEvent(
					meetingId.getValue());

			apply(meetingCommentingEnabled);
			addDomainEvent(meetingCommentingEnabled);
		}
	}

	public void disableCommenting(MemberId disablingMemberId, MeetingGroup meetingGroup) {
        checkRule(new MeetingCommentingCanBeDisabledOnlyByGroupOrganizerRule(disablingMemberId, meetingGroup));

		if (isCommentingEnabled) {	
			var meetingCommentingDisabled = new MeetingCommentingDisabledEvent(
					meetingId.getValue());

			apply(meetingCommentingDisabled);
			addDomainEvent(meetingCommentingDisabled);
		}
	}	

	private boolean when(MeetingCommentingDisabledEvent event) {
		isCommentingEnabled = false;
		return true;
	}

	private boolean when(MeetingCommentingConfigurationCreatedEvent event) {
		meetingCommentingConfigurationId = new MeetingCommentingConfigurationId(event.getMeetingCommentingConfigurationId());
		meetingId = new MeetingId(event.getMeetingId());
		isCommentingEnabled = event.getIsEnabled();
		return true;
	}

	private boolean when(MeetingCommentingEnabledEvent event) {
		isCommentingEnabled = true;
		return true;
	}

	@Override
	protected boolean apply(IDomainEvent event) {

		var processed = false;

		if (event instanceof MeetingCommentingDisabledEvent castedEvent) {
			processed = when(castedEvent);
		}
		else if (event instanceof MeetingCommentingConfigurationCreatedEvent castedEvent) {
			processed = when(castedEvent);
		}
		else if (event instanceof MeetingCommentingEnabledEvent castedEvent) {
			processed = when(castedEvent);
		}
		
		if (processed)
			incrementVersion();
			
		return processed;
	}

	@Override
	public List<Entity> getDirectChildEntities() {
		var entities = new ArrayList<Entity>();
		return entities;
	}


}
