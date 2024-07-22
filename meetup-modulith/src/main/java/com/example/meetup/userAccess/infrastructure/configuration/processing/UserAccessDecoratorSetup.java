package com.example.meetup.userAccess.infrastructure.configuration.processing;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;

import com.example.meetup.userAccess.base.application.IAsyncCommandHandler;
import com.example.meetup.userAccess.base.application.ICommandHandler;
import com.example.meetup.userAccess.base.application.IExecutionContextAccessor;
import com.example.meetup.userAccess.base.application.ISyncCommandHandler;
import com.example.meetup.userAccess.base.domain.IAggregateStore;

@Configuration
public class UserAccessDecoratorSetup implements BeanPostProcessor {

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
			var unitOfWorkDecorator = new UserAccessUnitOfWorkAsyncCommandHandlerDecorator((IAsyncCommandHandler) bean, aggregateStore);

			context.registerBean(beanName + "UserAccessUnitOfWorkAsyncCommandHandlerDecorator", UserAccessUnitOfWorkAsyncCommandHandlerDecorator.class, () -> unitOfWorkDecorator);

			// Logging Decorator
			var loggingDecorator = new UserAccessLoggingAsyncCommandHandlerDecorator(unitOfWorkDecorator);//, executionContextAccessor);

			context.registerBean(beanName + "UserAccessLoggingAsyncCommandHandlerDecorator", UserAccessLoggingAsyncCommandHandlerDecorator.class, () -> loggingDecorator);


			return loggingDecorator;
		}
		
		if (bean instanceof ISyncCommandHandler) {		
			
			// UnitOfWork Decorator			
			var unitOfWorkDecorator = new UserAccessUnitOfWorkSyncCommandHandlerDecorator((ISyncCommandHandler) bean, aggregateStore);

			context.registerBean(beanName + "UserAccessUnitOfWorkSyncCommandHandlerDecorator", UserAccessUnitOfWorkSyncCommandHandlerDecorator.class, () -> unitOfWorkDecorator);

			// Logging Decorator
			var loggingDecorator = new UserAccessLoggingSyncCommandHandlerDecorator(unitOfWorkDecorator);//, executionContextAccessor);

			context.registerBean(beanName + "UserAccessLoggingSyncCommandHandlerDecorator", UserAccessLoggingSyncCommandHandlerDecorator.class, () -> loggingDecorator);


			return loggingDecorator;
		} 

		return bean;
	}
}
