package com.example.meetup.administration.infrastructure;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.example.meetup.administration.base.application.ICommandHandler;
import com.example.meetup.administration.base.application.IQueryHandler;
import com.example.meetup.administration.base.application.ICommand;
import com.example.meetup.administration.base.infrastructure.IQueryDispatcher;
import com.example.meetup.administration.base.application.ISyncCommandHandler;
import com.example.meetup.administration.base.application.IAsyncCommandHandler;
import com.example.meetup.administration.base.application.IQuery;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AdministrationQueryDispatcher implements IQueryDispatcher {

    private final ApplicationContext applicationContext;

    @Override
    public <T> T executeQuery(IQuery query) {
        Class<? extends IQueryHandler> handlerType = query.getHandlerType();
        IQueryHandler handler = applicationContext.getBean(handlerType);
        return (T) handler.handle(query);
    }

}
