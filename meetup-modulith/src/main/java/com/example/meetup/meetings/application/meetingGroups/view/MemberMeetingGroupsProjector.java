package com.example.meetup.meetings.application.meetingGroups.view;

import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.meetup.meetings.base.application.IEvent;
import com.example.meetup.meetings.base.application.IProjector;
import com.example.meetup.meetings.base.infrastructure.IQueryDispatcher;
import com.example.meetup.meetings.domain.meetingGroup.events.MeetingGroupMemberLeftGroupEvent;
import com.example.meetup.meetings.domain.meetingGroup.events.NewMeetingGroupMemberJoinedEvent;
import com.example.meetup.meetings.domain.member.events.MemberCreatedEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberMeetingGroupsProjector implements IProjector {

	private final MemberMeetingGroupsRepository memberMeetingGroupsRepository;
	
	private final IQueryDispatcher queryDispatcher;
	
	private void when(NewMeetingGroupMemberJoinedEvent event) {
		var view = memberMeetingGroupsRepository.findByMemberId(event.getMemberId());
		
		MeetingGroupsView meetingGroup = queryDispatcher.executeQuery(new GetMeetingGroupsByIdQuery(event.getMeetingGroupId()));
		
		view.getMeetingGroups().add(new MeetingGroup(
				UUID.randomUUID(),
				meetingGroup.getMeetingGroupId(),
				meetingGroup.getLocationCountryCode(),
				meetingGroup.getMemberCount(),
				meetingGroup.getLocationCity(),
				meetingGroup.getDescription(),
				meetingGroup.getName(),
				event.getRole(),
				event.getJoinedDate()));		
	}
	private void when(MemberCreatedEvent event) {
		memberMeetingGroupsRepository.save(
			new MemberMeetingGroupsView(
				event.getMemberId(),
				null));
	}
	private void when(MeetingGroupMemberLeftGroupEvent event) {
		var view = memberMeetingGroupsRepository.findByMemberId(event.getMemberId());
		
		var meetingGroupToRemove = view.getMeetingGroups().stream()
				.filter(g -> g.getMeetingGroupId().equals(event.getMeetingGroupId())).findAny().get();
		
		view.getMeetingGroups().remove(meetingGroupToRemove);
	}
	
	@Override
//	@Transactional//(propagation = Propagation.REQUIRES_NEW)
	public void project(IEvent event) {
		
		if (event instanceof NewMeetingGroupMemberJoinedEvent castedEvent) {
			when(castedEvent);
			return;
		}
		if (event instanceof MemberCreatedEvent castedEvent) {
			when(castedEvent);
			return;
		}
		if (event instanceof MeetingGroupMemberLeftGroupEvent castedEvent) {
			when(castedEvent);
			return;
		}
			
	}
	
}
