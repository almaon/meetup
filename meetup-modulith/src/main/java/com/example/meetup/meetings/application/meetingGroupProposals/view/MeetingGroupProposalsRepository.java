package com.example.meetup.meetings.application.meetingGroupProposals.view;

import java.util.List;

import java.util.UUID;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface MeetingGroupProposalsRepository extends JpaRepository<MeetingGroupProposalsView, UUID> {

	MeetingGroupProposalsView findByMeetingGroupProposalId(UUID meetingGroupProposalId);
	


}
