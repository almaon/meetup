package com.example.meetup.administration.application.adminMeetingGroupProposals.view;

import java.util.List;

import java.util.UUID;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface AdminMeetingGroupProposalsRepository extends JpaRepository<AdminMeetingGroupProposalsView, UUID> {

	AdminMeetingGroupProposalsView findByMeetingGroupProposalId(UUID meetingGroupProposalId);
	


}
