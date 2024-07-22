package com.example.meetup.meetings.application.meetingCommentingConfigurations.view;

import java.util.List;

import java.util.UUID;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface MeetingCommentingConfigurationsRepository extends JpaRepository<MeetingCommentingConfigurationsView, UUID> {

	MeetingCommentingConfigurationsView findByMeetingId(UUID meetingId);
	


}
