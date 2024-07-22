package com.example.meetup.meetings.application.meetings;


import java.util.UUID;

import org.springframework.stereotype.Component;

import com.example.meetup.meetings.base.application.ISyncCommandHandler;
import com.example.meetup.meetings.base.domain.IAggregateStore;
import com.example.meetup.meetings.domain.MoneyValue;
import com.example.meetup.meetings.domain.meeting.MeetingLocation;
import com.example.meetup.meetings.domain.meeting.MeetingTerm;
import com.example.meetup.meetings.domain.meeting.Term;
import com.example.meetup.meetings.domain.meetingGroup.MeetingGroup;
import com.example.meetup.meetings.domain.meetingGroup.MeetingGroupId;
import com.example.meetup.meetings.domain.member.MemberId;
import com.example.meetup.meetings.infrastructure.MeetingContext;

import lombok.RequiredArgsConstructor;


    
@Component
@RequiredArgsConstructor
public class CreateMeetingCommandHandler implements ISyncCommandHandler<CreateMeetingCommand, UUID> {
	
	private final IAggregateStore aggregateStore;

	private final MeetingContext context;

	
	@Override
	public UUID handle(CreateMeetingCommand command) {	

   		MeetingGroup meetingGroup = aggregateStore.load(
   				new MeetingGroupId(command.getMeetingGroupId()), 
   				MeetingGroup.class);
    	
   		var meeting = meetingGroup.createMeeting(
   				command.getTitle(), 
   				new MeetingTerm(command.getTermStartDate(),command.getTermEndDate()), 
   				command.getDescription(),
   				new MeetingLocation(
   						command.getMeetingLocationName(), 
   						command.getMeetingLocationAddress(), 
   						command.getMeetingLocationPostalCode(), 
   						command.getMeetingLocationCity()), 
   				command.getAttendeesLimit(), 
   				command.getGuestsLimit(), 
   				new Term(command.getRsvpTermStartDate(), command.getRsvpTermEndDate()), 
   				new MoneyValue(command.getEventFeeValue(), command.getEventFeeCurrency()), 
   				command.getHostMemberIds() != null 
   					? command.getHostMemberIds().stream().map(uuid -> new MemberId(uuid)).toList()
   					: null, 
   				context.principalId());
		
		aggregateStore.save(meeting);
		return meeting.getMeetingId().getValue();

	}	
    	
}
