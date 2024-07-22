package com.example.meetup.meetings.domain.meetingGroup.rules;

import java.util.List;

import com.example.meetup.meetings.base.domain.IBusinessRule;
import com.example.meetup.meetings.domain.meetingGroup.MeetingGroupMember;
import com.example.meetup.meetings.domain.member.MemberId;

public class NotActualGroupMemberCannotLeaveGroupRule implements IBusinessRule {

	private List<MeetingGroupMember> members;

	private MemberId memberId;

	public NotActualGroupMemberCannotLeaveGroupRule(List<MeetingGroupMember> members, MemberId memberId) {
		this.members = members;
		this.memberId = memberId;
	}

	@Override
	public boolean isBroken() {
		return members.stream().filter(x -> x.isMember(memberId)).findAny().isEmpty();
	}

	@Override
	public String message() {
		return "Member is not member of this group so he cannot leave it";
	}
}
