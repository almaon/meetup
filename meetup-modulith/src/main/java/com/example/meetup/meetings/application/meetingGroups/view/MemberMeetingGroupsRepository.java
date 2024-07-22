package com.example.meetup.meetings.application.meetingGroups.view;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberMeetingGroupsRepository extends JpaRepository<MemberMeetingGroupsView, UUID> {

	MemberMeetingGroupsView findByMemberId(UUID memberId);

}
