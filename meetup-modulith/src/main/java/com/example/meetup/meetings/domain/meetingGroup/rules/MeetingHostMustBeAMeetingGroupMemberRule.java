package com.example.meetup.meetings.domain.meetingGroup.rules;

import java.util.List;

import com.example.meetup.meetings.base.domain.IBusinessRule;
import com.example.meetup.meetings.domain.meetingGroup.MeetingGroupMember;
import com.example.meetup.meetings.domain.member.MemberId;

public class MeetingHostMustBeAMeetingGroupMemberRule implements IBusinessRule {

	private MemberId creatorId;

	private List<MemberId> hostsMembersIds;

	private List<MeetingGroupMember> members;

	public MeetingHostMustBeAMeetingGroupMemberRule(MemberId creatorId, List<MemberId> hostsMembersIds,
			List<MeetingGroupMember> members) {
		this.creatorId = creatorId;
		this.hostsMembersIds = hostsMembersIds;
		this.members = members;
	}

	@Override
	public boolean isBroken() {
		var memberIds = members.stream().map(x -> x.getMemberId()).toList();
		if (hostsMembersIds.isEmpty() && !memberIds.contains(creatorId)) {
			return true;
		}

		return !hostsMembersIds.isEmpty()
				&& !hostsMembersIds.stream().filter(x -> !memberIds.contains(x)).toList().isEmpty();
	}

	@Override
	public String message() {
		return "Meeting host must be a meeting group member";
	}
}
