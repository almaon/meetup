package com.example.meetup.administration.infrastructure.configuration.processing;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;

import com.example.meetup.administration.base.application.IAsyncCommandHandler;
import com.example.meetup.administration.base.application.ICommandHandler;
import com.example.meetup.administration.base.application.IExecutionContextAccessor;
import com.example.meetup.administration.base.application.ISyncCommandHandler;
import com.example.meetup.administration.base.domain.IAggregateStore;

@Configuration
public class AdministrationDecoratorSetup implements BeanPostProcessor {

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
			var unitOfWorkDecorator = new AdministrationUnitOfWorkAsyncCommandHandlerDecorator((IAsyncCommandHandler) bean, aggregateStore);

			context.registerBean(beanName + "AdministrationUnitOfWorkAsyncCommandHandlerDecorator", AdministrationUnitOfWorkAsyncCommandHandlerDecorator.class, () -> unitOfWorkDecorator);

			// Logging Decorator
			var loggingDecorator = new AdministrationLoggingAsyncCommandHandlerDecorator( unitOfWorkDecorator);//, executionContextAccessor);

			context.registerBean(beanName + "AdministrationLoggingAsyncCommandHandlerDecorator", AdministrationLoggingAsyncCommandHandlerDecorator.class, () -> loggingDecorator);


			return loggingDecorator;
		}
		
		if (bean instanceof ISyncCommandHandler) {		
			
			// UnitOfWork Decorator			
			var unitOfWorkDecorator = new AdministrationUnitOfWorkSyncCommandHandlerDecorator((ISyncCommandHandler) bean, aggregateStore);

			context.registerBean(beanName + "AdministrationUnitOfWorkSyncCommandHandlerDecorator", AdministrationUnitOfWorkSyncCommandHandlerDecorator.class, () -> unitOfWorkDecorator);

			// Logging Decorator
			var loggingDecorator = new AdministrationLoggingSyncCommandHandlerDecorator( unitOfWorkDecorator);//, executionContextAccessor);

			context.registerBean(beanName + "AdministrationLoggingSyncCommandHandlerDecorator", AdministrationLoggingSyncCommandHandlerDecorator.class, () -> loggingDecorator);


			return loggingDecorator;
		} 

		return bean;
	}
}
