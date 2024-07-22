package com.example.meetup.meetings.application.meetingGroups.view;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingGroupsRepository extends JpaRepository<MeetingGroupsView, UUID> {

	MeetingGroupsView findByMeetingGroupId(UUID meetingGroupId);

}
