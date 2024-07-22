package com.example.meetup.meetings.application.meetingGroups.view;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.meetup.meetings.application.members.view.GetAllMemberQuery;
import com.example.meetup.meetings.application.members.view.GetMemberByIdQuery;
import com.example.meetup.meetings.application.members.view.MemberView;
import com.example.meetup.meetings.base.application.IEvent;
import com.example.meetup.meetings.base.application.IProjector;
import com.example.meetup.meetings.base.infrastructure.IQueryDispatcher;
import com.example.meetup.meetings.domain.meetingGroup.events.MeetingGroupCreatedEvent;
import com.example.meetup.meetings.domain.meetingGroup.events.MeetingGroupGeneralAttributesEditedEvent;
import com.example.meetup.meetings.domain.meetingGroup.events.MeetingGroupMemberLeftGroupEvent;
import com.example.meetup.meetings.domain.meetingGroup.events.MeetingGroupPaymentInfoUpdatedEvent;
import com.example.meetup.meetings.domain.meetingGroup.events.NewMeetingGroupMemberJoinedEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MeetingGroupsProjector implements IProjector {

	private final MeetingGroupsRepository meetingGroupsRepository;
	
	private final IQueryDispatcher queryDispatcher;
	
	private void when(MeetingGroupCreatedEvent event) {

		meetingGroupsRepository.save(
			new MeetingGroupsView(
				event.getMeetingGroupId(),
				event.getName(),
				event.getDescription(),
				event.getLocationCountryCode(),
				event.getLocationCity(),
				0,
				new ArrayList<>(),
				null));
	}
	
	private void when(NewMeetingGroupMemberJoinedEvent event) {

		var view = meetingGroupsRepository.findByMeetingGroupId(event.getMeetingGroupId());
		
		MemberView member = queryDispatcher.executeQuery(new GetMemberByIdQuery(event.getMemberId()));
		
		List<MemberView> allmember = queryDispatcher.executeQuery(new GetAllMemberQuery());

		view.getMeetingGroupMembers().add(
				new MeetingGroupMember(
						UUID.randomUUID(),
						event.getMemberId(),
						member.getLogin(),
						member.getEmail(),
						event.getRole()));

		view.setMemberCount(view.getMemberCount() + 1);
	}
	
	private void when(MeetingGroupPaymentInfoUpdatedEvent event) {

		var view = meetingGroupsRepository.findByMeetingGroupId(event.getMeetingGroupId());
		
		view.setPaymentDateTo(event.getPaymentDateTo());
	}
	
	private void when(MeetingGroupGeneralAttributesEditedEvent event) {

		var view = meetingGroupsRepository.findByMeetingGroupId(event.getMeetingGroupId());
		
		view.setName(event.getNewName());
		view.setDescription(event.getNewDescription());
		view.setLocationCountryCode(event.getNewLocationCountryCode());
		view.setLocationCity(event.getNewLocationCity());
	}
	
	private void when(MeetingGroupMemberLeftGroupEvent event) {

		var view = meetingGroupsRepository.findByMeetingGroupId(event.getMeetingGroupId());
		
		var memberToRemove = view.getMeetingGroupMembers().stream().filter(m -> m.getMemberId().equals(event.getMemberId())).findAny().get();
		
		view.getMeetingGroupMembers().remove(memberToRemove);
		view.setMemberCount(view.getMemberCount() - 1);
	}
	
	@Override
//	@Transactional//(propagation = Propagation.REQUIRES_NEW)
	public void project(IEvent event) {
		
		if (event instanceof MeetingGroupCreatedEvent castedEvent) {
			when(castedEvent);
			return;
		}
		if (event instanceof NewMeetingGroupMemberJoinedEvent castedEvent) {
			when(castedEvent);
			return;
		}
		if (event instanceof MeetingGroupPaymentInfoUpdatedEvent castedEvent) {
			when(castedEvent);
			return;
		}
		if (event instanceof MeetingGroupGeneralAttributesEditedEvent castedEvent) {
			when(castedEvent);
			return;
		}
		if (event instanceof MeetingGroupMemberLeftGroupEvent castedEvent) {
			when(castedEvent);
			return;
		}
			
	}
	
}
