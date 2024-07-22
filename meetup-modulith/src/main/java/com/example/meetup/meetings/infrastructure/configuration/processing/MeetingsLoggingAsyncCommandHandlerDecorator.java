package com.example.meetup.meetings.infrastructure.configuration.processing;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.meetup.meetings.base.SystemClock;
import com.example.meetup.meetings.base.application.IAsyncCommandHandler;
import com.example.meetup.meetings.base.application.ICommand;
import com.example.meetup.meetings.base.application.IExecutionContextAccessor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j(topic = "Command")
public class MeetingsLoggingAsyncCommandHandlerDecorator implements IAsyncCommandHandler {

	private final IAsyncCommandHandler decorated;
	
//    private final  IExecutionContextAccessor executionContextAccessor;

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void handle(ICommand command) {
		
		// TODO: better enrichment method
		String logEnricher = " id:" + command.getId();
//		if (executionContextAccessor.isAvailable())
//			logEnricher += " CORRELATION_ID: " + executionContextAccessor.getCorrelationId();
		
		try {
			log.info("Executing command " + command.getClass().getSimpleName() + logEnricher + "\t system time:" + SystemClock.now());
			
			decorated.handle(command);
			
			log.info("Command " + command.getClass().getSimpleName() + " processed successsful" + logEnricher + "\t system time:" + SystemClock.now());
		} catch (Exception e) {
			log.error("Command " + command.getClass().getSimpleName() + " processing failed" 
					+ logEnricher + "\t system time:" + SystemClock.now() + "\n" + e.getMessage());
			throw e;
		}
	
	}

}
