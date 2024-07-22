package com.example.meetup.meetings.application.meetingComments.view;

import java.util.List;

import java.util.UUID;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface MeetingCommentsRepository extends JpaRepository<MeetingCommentsView, UUID> {

	MeetingCommentsView findByMeetingCommentId(UUID meetingCommentId);
	


}
