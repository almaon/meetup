package com.example.meetup.meetings.infrastructure;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;



import com.example.meetup.meetings.base.application.ICommandHandler;
import com.example.meetup.meetings.base.application.IQueryHandler;
import com.example.meetup.meetings.base.application.ICommand;
import com.example.meetup.meetings.base.infrastructure.ICommandDispatcher;
import com.example.meetup.meetings.base.application.ISyncCommandHandler;
import com.example.meetup.meetings.base.application.IAsyncCommandHandler;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MeetingsCommandDispatcher implements ICommandDispatcher {

    private final ApplicationContext applicationContext;

    @Override
    public <T> T executeCommandSync(ICommand command) {
        Class<? extends ICommandHandler> handlerType = command.getHandlerType();
        var beanName = handlerType.getSimpleName().substring(0, 1).toLowerCase() + handlerType.getSimpleName().substring(1);
        var handler = (ISyncCommandHandler) applicationContext.getBean(beanName);
        return (T) handler.handle(command);
    }
    
    @Override
//    @Transactional
	@Async
    public void executeCommandAsync(ICommand command) {
        Class<? extends ICommandHandler> handlerType = command.getHandlerType();
        var beanName = handlerType.getSimpleName().substring(0, 1).toLowerCase() + handlerType.getSimpleName().substring(1);
        var handler = (IAsyncCommandHandler) applicationContext.getBean(beanName);
        handler.handle(command);
    }

}
