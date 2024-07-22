package com.example.meetup.meetings.domain.meetingGroup.events;


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
public class MeetingGroupGeneralAttributesEditedEvent extends DomainEventBase {


	private String newName;
	private String newDescription;
	private UUID meetingGroupId;
	private String newLocationCity;
	private String newLocationCountryCode;
	
}
