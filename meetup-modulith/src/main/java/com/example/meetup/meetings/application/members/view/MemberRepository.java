package com.example.meetup.meetings.application.members.view;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberView, UUID> {

	MemberView findByMemberId(UUID memberId);
	
	MemberView findByLogin(String login);
}
