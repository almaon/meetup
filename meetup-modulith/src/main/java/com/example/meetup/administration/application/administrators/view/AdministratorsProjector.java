package com.example.meetup.administration.application.administrators.view;


import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import com.example.meetup.administration.base.application.IProjector;
import com.example.meetup.administration.base.application.IEvent;

import com.example.meetup.administration.domain.administrator.events.AdminCreatedEvent;

import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class AdministratorsProjector implements IProjector {

	private final AdministratorsRepository administratorsRepository;
	


	
	private void when(AdminCreatedEvent event) {

		administratorsRepository.save(
			new AdministratorsView(
				event.getAdminId(),
				event.getEmail(),
				event.getFirstName(),
				event.getRegisterDate(),
				event.getLastName(),
				event.getLogin()));

	}
	
	@Override
//	@Transactional//(propagation = Propagation.REQUIRES_NEW)
	public void project(IEvent event) {
		
		if (event instanceof AdminCreatedEvent castedEvent) {
			when(castedEvent);
			return;
		}
			
	}
	
}
