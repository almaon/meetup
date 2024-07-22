package com.example.meetup.meetings.infrastructure.configuration.processing;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;

import com.example.meetup.meetings.base.application.IAsyncCommandHandler;
import com.example.meetup.meetings.base.application.ICommandHandler;
import com.example.meetup.meetings.base.application.IExecutionContextAccessor;
import com.example.meetup.meetings.base.application.ISyncCommandHandler;
import com.example.meetup.meetings.base.domain.IAggregateStore;

@Configuration
public class MeetingsDecoratorSetup implements BeanPostProcessor {

	@Autowired
	private IAggregateStore aggregateStore;
	
	@Autowired
	private GenericApplicationContext context;

//	@Autowired
//	private IExecutionContextAccessor executionContextAccessor;
	
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

		if (bean instanceof IAsyncCommandHandler) {		
	
			// UnitOfWork Decorator			
			var unitOfWorkDecorator = new MeetingsUnitOfWorkAsyncCommandHandlerDecorator((IAsyncCommandHandler) bean, aggregateStore);

			context.registerBean(beanName + "MeetingsUnitOfWorkAsyncCommandHandlerDecorator", MeetingsUnitOfWorkAsyncCommandHandlerDecorator.class, () -> unitOfWorkDecorator);

			// Logging Decorator
			var loggingDecorator = new MeetingsLoggingAsyncCommandHandlerDecorator( unitOfWorkDecorator);//, executionContextAccessor);

			context.registerBean(beanName + "MeetingsLoggingAsyncCommandHandlerDecorator", MeetingsLoggingAsyncCommandHandlerDecorator.class, () -> loggingDecorator);


			return loggingDecorator;
		}
		
		if (bean instanceof ISyncCommandHandler) {		
			
			// UnitOfWork Decorator			
			var unitOfWorkDecorator = new MeetingsUnitOfWorkSyncCommandHandlerDecorator((ISyncCommandHandler) bean, aggregateStore);

			context.registerBean(beanName + "MeetingsUnitOfWorkSyncCommandHandlerDecorator", MeetingsUnitOfWorkSyncCommandHandlerDecorator.class, () -> unitOfWorkDecorator);

			// Logging Decorator
			var loggingDecorator = new MeetingsLoggingSyncCommandHandlerDecorator( unitOfWorkDecorator);//, executionContextAccessor);

			context.registerBean(beanName + "MeetingsLoggingSyncCommandHandlerDecorator", MeetingsLoggingSyncCommandHandlerDecorator.class, () -> loggingDecorator);


			return loggingDecorator;
		} 

		return bean;
	}
}
