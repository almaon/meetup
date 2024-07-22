package com.example.meetup.payments.infrastructure.configuration.processing;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.example.meetup.payments.base.application.ISyncCommandHandler;
import com.example.meetup.payments.base.application.ICommand;
import com.example.meetup.payments.base.domain.IAggregateStore;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PaymentsUnitOfWorkSyncCommandHandlerDecorator implements ISyncCommandHandler {

	private final ISyncCommandHandler decorated;

	private final IAggregateStore aggregateStore;

	@Override
	@Transactional
	public Object handle(ICommand command) {
		
		var result =  decorated.handle(command);
				
		return result;
	}

}
