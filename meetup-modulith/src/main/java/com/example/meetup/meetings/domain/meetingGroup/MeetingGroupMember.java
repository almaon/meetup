package com.example.meetup.meetings.domain.meetingGroup;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.meetup.meetings.base.SystemClock;
import com.example.meetup.meetings.base.domain.Aggregate;
import com.example.meetup.meetings.base.domain.Entity;
import com.example.meetup.meetings.base.domain.IDomainEvent;
import com.example.meetup.meetings.domain.meetingGroup.events.MeetingGroupMemberLeftGroupEvent;
import com.example.meetup.meetings.domain.member.MemberId;

import lombok.Getter;

@Getter
public class MeetingGroupMember extends Entity {

	// business id
	protected MemberId memberId;
	protected MeetingGroupMemberRole role;
	protected MeetingGroupId meetingGroupId;
	protected LocalDateTime joinedDate;
	protected Boolean isActive;
	protected LocalDateTime leaveDate;

	MeetingGroupMember(Aggregate root, MeetingGroupId meetingGroupId, MemberId memberId, MeetingGroupMemberRole role, LocalDateTime joinedDate) {
		super(root);

		this.memberId = memberId;
		this.meetingGroupId = meetingGroupId;
		this.role = role;
		this.joinedDate = joinedDate;
		isActive = true;
	}

	void leave() {
		var meetingGroupMemberLeftGroup = new MeetingGroupMemberLeftGroupEvent(
				meetingGroupId.getValue(),
				memberId.getValue(), 
				SystemClock.now());

		apply(meetingGroupMemberLeftGroup);
		addDomainEvent(meetingGroupMemberLeftGroup);
	}
	
	public void rejoin() {
		leaveDate = null;
		isActive = true;
	}

	public boolean isMember(MemberId memberId) {
		return isActive && this.memberId.equals(memberId);
	}

	boolean isOrganizer(MemberId memberId) {
		return isMember(memberId) && role == MeetingGroupMemberRole.Organizer;
	}	

	private boolean when(MeetingGroupMemberLeftGroupEvent event) {
		if (new MemberId(event.getMemberId()).equals(memberId)) {
			isActive = false;
			leaveDate = event.getLeaveDate();

			return true;
		}
		return false;
	}

	@Override
	protected boolean apply(IDomainEvent event) {

		var processed = false;
		
		if (event instanceof MeetingGroupMemberLeftGroupEvent castedEvent) {
			processed = when(castedEvent);
		}
		
		if (processed)
			aggregateRoot.incrementVersion();
			
		return processed;
	}

	@Override
	public List<Entity> getDirectChildEntities() {
		var entities = new ArrayList<Entity>();
		return entities;
	}

}
