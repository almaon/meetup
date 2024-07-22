package com.example.meetup.administration.infrastructure.configuration.processing;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import com.example.meetup.administration.base.application.IAsyncCommandHandler;
import com.example.meetup.administration.base.application.ICommand;
import com.example.meetup.administration.base.application.IExecutionContextAccessor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j(topic = "Command")
public class AdministrationLoggingAsyncCommandHandlerDecorator implements IAsyncCommandHandler {

	private final IAsyncCommandHandler decorated;
	
//    private final  IExecutionContextAccessor executionContextAccessor;

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void handle(ICommand command) {
		
		// TODO: better enrichment method
		String logEnricher = " CONTEXT: Command:" + command.getId();
//		if (executionContextAccessor.isAvailable())
//			logEnricher += " CORRELATION_ID: " + executionContextAccessor.getCorrelationId();
		
		try {
			log.info("Executing command " + command.getClass().getSimpleName() + logEnricher);
			
			decorated.handle(command);
			
			log.info("Command " + command.getClass().getSimpleName() + " processed successsful" + logEnricher);
		} catch (Exception e) {
			log.error("Command " + command.getClass().getSimpleName() + " processing failed" + logEnricher);
			throw e;
		}
	
	}

}
