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
public class MeetingGroupCreatedEvent extends DomainEventBase {


	private UUID meetingGroupId;
	private UUID creatorId;
	private String description;
	private String locationCity;
	private String name;
	private String locationCountryCode;
	
}
