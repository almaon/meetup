package com.example.meetup.meetings.infrastructure.configuration.processing;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import com.example.meetup.meetings.base.application.ISyncCommandHandler;
import com.example.meetup.meetings.base.SystemClock;
import com.example.meetup.meetings.base.application.ICommand;
import com.example.meetup.meetings.base.application.IExecutionContextAccessor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j(topic = "Command")
public class MeetingsLoggingSyncCommandHandlerDecorator implements ISyncCommandHandler {

	private final ISyncCommandHandler decorated;
	
//    private final  IExecutionContextAccessor executionContextAccessor;

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Object handle(ICommand command) {
		
		// TODO: better enrichment method
		String logEnricher = " CONTEXT: Command:" + command.getId();
//		if (executionContextAccessor.isAvailable())
//			logEnricher += " CORRELATION_ID: " + executionContextAccessor.getCorrelationId();
		
		try {
			log.info("Executing command " + command.getClass().getSimpleName() + "\t system time:" + SystemClock.now());
			
			var result = decorated.handle(command);
			
			log.info("Command " + command.getClass().getSimpleName() + " processed successsful" + "\t system time:" + SystemClock.now());
			
			return result;
		} catch (Exception e) {
			log.error("Command " + command.getClass().getSimpleName() + " processing failed" 
					+ logEnricher + "\t system time:" + SystemClock.now() + "\n" + e.getMessage());
			throw e;
		}
	
	}

}
