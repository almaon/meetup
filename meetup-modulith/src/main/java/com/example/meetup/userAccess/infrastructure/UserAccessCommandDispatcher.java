package com.example.meetup.userAccess.infrastructure;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;



import com.example.meetup.userAccess.base.application.ICommandHandler;
import com.example.meetup.userAccess.base.application.IQueryHandler;
import com.example.meetup.userAccess.base.application.ICommand;
import com.example.meetup.userAccess.base.infrastructure.ICommandDispatcher;
import com.example.meetup.userAccess.base.application.ISyncCommandHandler;
import com.example.meetup.userAccess.base.application.IAsyncCommandHandler;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserAccessCommandDispatcher implements ICommandDispatcher {

    private final ApplicationContext applicationContext;

    @Override
    public <T> T executeSync(ICommand command) {
        Class<? extends ICommandHandler> handlerType = command.getHandlerType();
        var beanName = handlerType.getSimpleName().substring(0, 1).toLowerCase() + handlerType.getSimpleName().substring(1);
        var handler = (ISyncCommandHandler) applicationContext.getBean(beanName);
        return (T) handler.handle(command);
    }
    
    @Override
    @Transactional
	@Async
    public void executeAsync(ICommand command) {
        Class<? extends ICommandHandler> handlerType = command.getHandlerType();
        var beanName = handlerType.getSimpleName().substring(0, 1).toLowerCase() + handlerType.getSimpleName().substring(1);
        var handler = (IAsyncCommandHandler) applicationContext.getBean(beanName);
        handler.handle(command);
    }

}
