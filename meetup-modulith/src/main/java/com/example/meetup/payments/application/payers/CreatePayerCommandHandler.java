package com.example.meetup.payments.application.payers;


import java.util.UUID;

import org.springframework.stereotype.Component;

import com.example.meetup.payments.base.application.ISyncCommandHandler;
import com.example.meetup.payments.base.domain.IAggregateStore;
import com.example.meetup.payments.domain.payers.Payer;
import com.example.meetup.payments.domain.payers.PayerId;

import lombok.RequiredArgsConstructor;

    
@Component
@RequiredArgsConstructor
public class CreatePayerCommandHandler implements ISyncCommandHandler<CreatePayerCommand, UUID> {
	
	private final IAggregateStore aggregateStore;


	
	@Override
	public UUID handle(CreatePayerCommand command) {	

		 var payer = new Payer(
				 new PayerId(command.getPayerId()),
				 command.getLogin(),
				 command.getEmail(),
				 command.getFirstName(),
				 command.getLastName(),
				 command.getRegisterDate());
    	
		
		aggregateStore.save(payer);
		return payer.getPayerId().getValue();

	}	
    	
}
