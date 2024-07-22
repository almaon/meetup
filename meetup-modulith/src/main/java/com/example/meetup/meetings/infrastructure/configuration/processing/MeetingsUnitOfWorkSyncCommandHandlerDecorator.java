package com.example.meetup.meetings.infrastructure.configuration.processing;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.example.meetup.meetings.base.application.ISyncCommandHandler;
import com.example.meetup.meetings.base.application.ICommand;
import com.example.meetup.meetings.base.domain.IAggregateStore;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MeetingsUnitOfWorkSyncCommandHandlerDecorator implements ISyncCommandHandler {

	private final ISyncCommandHandler decorated;

	private final IAggregateStore aggregateStore;

	@Override
	@Transactional
	public Object handle(ICommand command) {
		
		var result =  decorated.handle(command);
				
		return result;
	}

}
