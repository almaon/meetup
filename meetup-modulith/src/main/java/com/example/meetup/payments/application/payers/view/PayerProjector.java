package com.example.meetup.payments.application.payers.view;


import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import com.example.meetup.payments.base.application.IProjector;
import com.example.meetup.payments.base.application.IEvent;

import com.example.meetup.payments.domain.payers.events.PayerCreatedEvent;

import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class PayerProjector implements IProjector {

	private final PayerRepository payerRepository;
	


	
	private void when(PayerCreatedEvent event) {

		payerRepository.save(
			new PayerView(
				event.getPayerId(),
				event.getLogin(),
				event.getFirstName(),
				event.getLastName(),
				event.getEmail(),
				event.getRegisterDate()));

	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void project(IEvent event) {
		
		if (event instanceof PayerCreatedEvent castedEvent) {
			when(castedEvent);
			return;
		}
			
	}
	
}
