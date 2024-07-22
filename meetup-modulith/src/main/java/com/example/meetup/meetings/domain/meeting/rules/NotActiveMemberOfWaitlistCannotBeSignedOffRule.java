package com.example.meetup.meetings.domain.meeting.rules;

import java.util.List;

import com.example.meetup.meetings.base.domain.IBusinessRule;
import com.example.meetup.meetings.domain.meeting.MeetingWaitlistMember;
import com.example.meetup.meetings.domain.member.MemberId;

public class NotActiveMemberOfWaitlistCannotBeSignedOffRule implements IBusinessRule {

	private List<MeetingWaitlistMember> waitlistMembers;

	private MemberId memberId;

	public NotActiveMemberOfWaitlistCannotBeSignedOffRule(List<MeetingWaitlistMember> waitlistMembers,
			MemberId memberId) {
		this.waitlistMembers = waitlistMembers;
		this.memberId = memberId;
	}

	@Override
	public boolean isBroken() {
		return waitlistMembers.stream().filter(x -> x.isActiveOnWaitList(memberId)).findAny().isEmpty();
	}

	@Override
	public String message() {
		return "Not active member of waitlist cannot be signed off";
	}

}
