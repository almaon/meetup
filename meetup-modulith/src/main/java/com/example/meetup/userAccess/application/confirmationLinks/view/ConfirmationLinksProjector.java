package com.example.meetup.userAccess.application.confirmationLinks.view;


import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import com.example.meetup.userAccess.base.application.IProjector;
import com.example.meetup.userAccess.base.application.IEvent;

import com.example.meetup.userAccess.domain.userRegistrations.events.UserRegistrationConfirmedEvent;
import com.example.meetup.userAccess.domain.userRegistrations.events.NewUserRegisteredEvent;

import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class ConfirmationLinksProjector implements IProjector {

	private final ConfirmationLinksRepository confirmationLinksRepository;
	


	
	private void when(NewUserRegisteredEvent event) {

		confirmationLinksRepository.save(
			new ConfirmationLinksView(
					event.getConfirmLink(),
					event.getLogin(),
					event.getUserRegistrationId()));

	}
	private void when(UserRegistrationConfirmedEvent event) {

		var view = confirmationLinksRepository.findByUserRegistrationId(event.getUserRegistrationId());
		
		confirmationLinksRepository.delete(view);

	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void project(IEvent event) {
		
		if (event instanceof NewUserRegisteredEvent castedEvent) {
			when(castedEvent);
			return;
		}
		if (event instanceof UserRegistrationConfirmedEvent castedEvent) {
			when(castedEvent);
			return;
		}
			
	}
	
}
