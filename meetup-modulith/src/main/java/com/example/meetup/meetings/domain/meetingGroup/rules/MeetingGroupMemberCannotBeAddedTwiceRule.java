package com.example.meetup.meetings.domain.meetingGroup.rules;

import java.util.List;

import com.example.meetup.meetings.base.domain.IBusinessRule;
import com.example.meetup.meetings.domain.meetingGroup.MeetingGroupMember;
import com.example.meetup.meetings.domain.member.MemberId;

public class MeetingGroupMemberCannotBeAddedTwiceRule implements IBusinessRule {

	private List<MeetingGroupMember> members;

	private MemberId memberId;

	public MeetingGroupMemberCannotBeAddedTwiceRule(List<MeetingGroupMember> members, MemberId memberId) {
		this.members = members;
		this.memberId = memberId;
	}

	@Override
	public boolean isBroken() {
		return members.stream().anyMatch(x -> x.isMember(memberId));
	}

	@Override
	public String message() {
		return "Member cannot be added twice to the same group";
	}
}
