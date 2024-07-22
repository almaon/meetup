package com.example.meetup.meetings.application.meetingGroups;

import org.springframework.stereotype.Component;

import com.example.meetup.meetings.application.meetingGroups.view.GetMemberMeetingGroupsByIdQuery;
import com.example.meetup.meetings.application.meetingGroups.view.MemberMeetingGroupsView;
import com.example.meetup.meetings.base.application.IEvent;
import com.example.meetup.meetings.base.application.IEventHandler;
import com.example.meetup.meetings.base.infrastructure.ICommandDispatcher;
import com.example.meetup.meetings.base.infrastructure.IQueryDispatcher;
import com.example.meetup.meetings.domain.meetingGroup.MeetingGroupMemberRole;
import com.example.meetup.meetings.domain.memberSubscriptions.events.MemberSubscriptionCreatedEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberSubscriptionCreatedEventListener implements IEventHandler<MemberSubscriptionCreatedEvent> {

	private final ICommandDispatcher commandDispatcher;

	private final IQueryDispatcher queryDispatcher;

	@Override
	public Class<? extends IEvent> registeredFor() {
		return MemberSubscriptionCreatedEvent.class;
	}

	@Override
	public void handle(MemberSubscriptionCreatedEvent event) {

		MemberMeetingGroupsView memberMeetingGroups = queryDispatcher
				.executeQuery(new GetMemberMeetingGroupsByIdQuery(event.getMemberId()));

		var meetingGroupsWhereMemberIsOrganizer = memberMeetingGroups.getMeetingGroups().stream()
				.filter(mg -> MeetingGroupMemberRole.valueOf(mg.getMemberRole()) == MeetingGroupMemberRole.Organizer)
				.toList();

		for (var meetingGroup : meetingGroupsWhereMemberIsOrganizer) {
			commandDispatcher.executeCommandAsync(new SetMeetingGroupExpirationDateCommand(
					meetingGroup.getMeetingGroupId(), event.getExpirationDate()));
		}

	}

}
