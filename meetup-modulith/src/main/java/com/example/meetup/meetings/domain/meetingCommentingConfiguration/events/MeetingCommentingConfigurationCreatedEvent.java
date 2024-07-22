package com.example.meetup.meetings.domain.meetingCommentingConfiguration.events;


import com.example.meetup.meetings.base.domain.DomainEventBase;

import java.util.UUID;
	
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter


@AllArgsConstructor
@NoArgsConstructor
public class MeetingCommentingConfigurationCreatedEvent extends DomainEventBase {


	private UUID meetingId;
	private Boolean isEnabled;
	private UUID meetingCommentingConfigurationId;
	
}
