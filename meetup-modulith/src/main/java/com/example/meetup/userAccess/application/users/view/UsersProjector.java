package com.example.meetup.userAccess.application.users.view;


import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import com.example.meetup.userAccess.base.application.IProjector;
import com.example.meetup.userAccess.base.application.IEvent;

import com.example.meetup.userAccess.domain.users.events.UserCreatedEvent;

import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class UsersProjector implements IProjector {

	private final UsersRepository usersRepository;
	


	
	private void when(UserCreatedEvent event) {

		usersRepository.save(
			new UsersView(
				event.getUserId(),
				event.getLastName(),
				event.getEmail(),
				event.getRegisterDate(),
				event.getLogin(),
				event.getFirstName(),
				event.getUserType()));

	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void project(IEvent event) {
		
		if (event instanceof UserCreatedEvent castedEvent) {
			when(castedEvent);
			return;
		}
			
	}
	
}
