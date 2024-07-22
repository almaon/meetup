package com.example.meetup.meetings.domain.member;

import java.util.List;
import com.example.meetup.meetings.domain.meetingGroup.MeetingGroupId;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import lombok.AllArgsConstructor;
@AllArgsConstructor


@Setter
@Getter
public class MeetingGroupMemberData {

	private MemberId memberId;
	private MeetingGroupId meetingGroupId;

	
	
}
