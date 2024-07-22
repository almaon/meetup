package com.example.meetup.payments.application.meetingFees.view;

import java.util.List;

import java.util.UUID;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface MeetingFeesRepository extends JpaRepository<MeetingFeesView, UUID> {

	MeetingFeesView findByMeetingFeeId(UUID meetingFeeId);
	


}
