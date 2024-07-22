package com.example.meetup.meetings.application.members.view;


import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import com.example.meetup.meetings.base.application.IProjector;
import com.example.meetup.meetings.base.application.IEvent;

import com.example.meetup.meetings.domain.member.events.MemberCreatedEvent;

import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class MemberProjector implements IProjector {

	private final MemberRepository memberRepository;
	


	
	private void when(MemberCreatedEvent event) {

		memberRepository.save(
			new MemberView(
				event.getMemberId(),
				event.getEmail(),
				event.getFirstName(),
				event.getRegisterDate(),
				event.getLastName(),
				event.getLogin()));

	}
	
	@Override
//	@Transactional//(propagation = Propagation.REQUIRES_NEW)
	public void project(IEvent event) {
		
		if (event instanceof MemberCreatedEvent castedEvent) {
			when(castedEvent);
			return;
		}
			
	}
	
}
