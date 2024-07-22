package com.example.meetup.userAccess.infrastructure;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.example.meetup.userAccess.base.application.ICommandHandler;
import com.example.meetup.userAccess.base.application.IQueryHandler;
import com.example.meetup.userAccess.base.application.ICommand;
import com.example.meetup.userAccess.base.infrastructure.IQueryDispatcher;
import com.example.meetup.userAccess.base.application.ISyncCommandHandler;
import com.example.meetup.userAccess.base.application.IAsyncCommandHandler;
import com.example.meetup.userAccess.base.application.IQuery;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserAccessQueryDispatcher implements IQueryDispatcher {

    private final ApplicationContext applicationContext;

    @Override
    public <T> T execute(IQuery query) {
        Class<? extends IQueryHandler> handlerType = query.getHandlerType();
        IQueryHandler handler = applicationContext.getBean(handlerType);
        return (T) handler.handle(query);
    }

}
