package com.example.meetup.meetings.application.meetings.view;

import java.util.List;

import java.util.UUID;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface MeetingAttendeesRepository extends JpaRepository<MeetingAttendeesView, UUID> {

	MeetingAttendeesView findByMeetingId(UUID meetingId);
	


}