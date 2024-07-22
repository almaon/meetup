package com.example.meetup.payments.infrastructure.configuration.processing;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.example.meetup.payments.base.application.IAsyncCommandHandler;
import com.example.meetup.payments.base.application.ICommand;
import com.example.meetup.payments.base.domain.IAggregateStore;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PaymentsUnitOfWorkAsyncCommandHandlerDecorator implements IAsyncCommandHandler {

	private final IAsyncCommandHandler decorated;

	private final IAggregateStore aggregateStore;

	@Override
	@Transactional
	public void handle(ICommand command) {
		
		decorated.handle(command);
	}

}
