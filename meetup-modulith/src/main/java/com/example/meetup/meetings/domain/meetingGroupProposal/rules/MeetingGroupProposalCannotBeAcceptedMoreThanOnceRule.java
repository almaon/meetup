package com.example.meetup.meetings.domain.meetingGroupProposal.rules;

import com.example.meetup.meetings.base.domain.IBusinessRule;
import com.example.meetup.meetings.domain.meetingGroupProposal.MeetingGroupProposalStatus;

public class MeetingGroupProposalCannotBeAcceptedMoreThanOnceRule implements IBusinessRule {

	private MeetingGroupProposalStatus actualStatus;

	public MeetingGroupProposalCannotBeAcceptedMoreThanOnceRule(MeetingGroupProposalStatus actualStatus) {
		this.actualStatus = actualStatus;
	}

	@Override
	public boolean isBroken() {
		return actualStatus == MeetingGroupProposalStatus.Accepted;
	}

	@Override
	public String message() {
		return "Meeting group proposal cannot be accepted more than once rule";
	}
}
