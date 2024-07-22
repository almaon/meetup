package com.example.meetup.payments.infrastructure.configuration.processing;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;

import com.example.meetup.payments.base.application.IAsyncCommandHandler;
import com.example.meetup.payments.base.application.ICommandHandler;
import com.example.meetup.payments.base.application.IExecutionContextAccessor;
import com.example.meetup.payments.base.application.ISyncCommandHandler;
import com.example.meetup.payments.base.domain.IAggregateStore;

@Configuration
public class PaymentsDecoratorSetup implements BeanPostProcessor {

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
			var unitOfWorkDecorator = new PaymentsUnitOfWorkAsyncCommandHandlerDecorator((IAsyncCommandHandler) bean, aggregateStore);

			context.registerBean(beanName + "PaymentsUnitOfWorkAsyncCommandHandlerDecorator", PaymentsUnitOfWorkAsyncCommandHandlerDecorator.class, () -> unitOfWorkDecorator);

			// Logging Decorator
			var loggingDecorator = new PaymentsLoggingAsyncCommandHandlerDecorator(unitOfWorkDecorator);//, executionContextAccessor);

			context.registerBean(beanName + "PaymentsLoggingAsyncCommandHandlerDecorator", PaymentsLoggingAsyncCommandHandlerDecorator.class, () -> loggingDecorator);


			return loggingDecorator;
		}
		
		if (bean instanceof ISyncCommandHandler) {		
			
			// UnitOfWork Decorator			
			var unitOfWorkDecorator = new PaymentsUnitOfWorkSyncCommandHandlerDecorator((ISyncCommandHandler) bean, aggregateStore);

			context.registerBean(beanName + "PaymentsUnitOfWorkSyncCommandHandlerDecorator", PaymentsUnitOfWorkSyncCommandHandlerDecorator.class, () -> unitOfWorkDecorator);

			// Logging Decorator
			var loggingDecorator = new PaymentsLoggingSyncCommandHandlerDecorator(unitOfWorkDecorator);//, executionContextAccessor);

			context.registerBean(beanName + "PaymentsLoggingSyncCommandHandlerDecorator", PaymentsLoggingSyncCommandHandlerDecorator.class, () -> loggingDecorator);


			return loggingDecorator;
		} 

		return bean;
	}
}
