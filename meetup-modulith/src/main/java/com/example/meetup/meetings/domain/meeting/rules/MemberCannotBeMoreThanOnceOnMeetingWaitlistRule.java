package com.example.meetup.meetings.domain.meeting.rules;

import java.util.List;

import com.example.meetup.meetings.base.domain.IBusinessRule;
import com.example.meetup.meetings.domain.meeting.MeetingWaitlistMember;
import com.example.meetup.meetings.domain.member.MemberId;

public class MemberCannotBeMoreThanOnceOnMeetingWaitlistRule implements IBusinessRule {

	private List<MeetingWaitlistMember> waitListMembers;

	private MemberId memberId;

	public MemberCannotBeMoreThanOnceOnMeetingWaitlistRule(List<MeetingWaitlistMember> waitListMembers,
			MemberId memberId) {
		this.waitListMembers = waitListMembers;
		this.memberId = memberId;
	}

	@Override
	public boolean isBroken() {
		return waitListMembers.stream().filter(x -> x.isActiveOnWaitList(memberId)).count() > 1;
	}

	@Override
	public String message() {
		return "Member cannot be more than once on the meeting waitlist";
	}

}
