package com.example.meetup.meetings.application.meetings;


import org.springframework.stereotype.Component;

import com.example.meetup.meetings.base.application.IAsyncCommandHandler;
import com.example.meetup.meetings.base.domain.IAggregateStore;
import com.example.meetup.meetings.domain.MoneyValue;
import com.example.meetup.meetings.domain.meeting.Meeting;
import com.example.meetup.meetings.domain.meeting.MeetingId;
import com.example.meetup.meetings.domain.meeting.MeetingLimits;
import com.example.meetup.meetings.domain.meeting.MeetingLocation;
import com.example.meetup.meetings.domain.meeting.MeetingTerm;
import com.example.meetup.meetings.domain.meeting.Term;
import com.example.meetup.meetings.infrastructure.MeetingContext;

import lombok.RequiredArgsConstructor;

    
@Component
@RequiredArgsConstructor
public class ChangeMeetingMainAttributesCommandHandler implements IAsyncCommandHandler<ChangeMeetingMainAttributesCommand> {

	private final IAggregateStore aggregateStore;

	private final MeetingContext context;

	@Override
	public void handle(ChangeMeetingMainAttributesCommand command) {

		Meeting meeting = aggregateStore.load(new MeetingId(command.getMeetingId()), Meeting.class);
    	
		meeting.changeMainAttributes(
				command.getTitle(), 
				new MeetingTerm(
						command.getTermStartDate(), 
						command.getTermEndDate()), 
				command.getDescription(), 
				new MeetingLocation(
						command.getMeetingLocationName(), 
						command.getMeetingLocationAddress(), 
						command.getMeetingLocationPostalCode(), 
						command.getMeetingLocationCity()), 
				new MeetingLimits(
						command.getAttendeesLimit(), 
						command.getGuestsLimit()), 
				new Term(
						command.getRsvpTermStartDate(), 
						command.getRsvpTermEndDate()), 
				new MoneyValue(
						command.getEventFeeValue(), 
						command.getEventFeeCurrency()), 
				context.principalId());
		
		aggregateStore.save(meeting);

	}	
    	
}
