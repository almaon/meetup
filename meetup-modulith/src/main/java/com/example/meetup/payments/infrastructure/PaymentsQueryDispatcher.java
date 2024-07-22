package com.example.meetup.payments.infrastructure;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.example.meetup.payments.base.application.ICommandHandler;
import com.example.meetup.payments.base.application.IQueryHandler;
import com.example.meetup.payments.base.application.ICommand;
import com.example.meetup.payments.base.infrastructure.IQueryDispatcher;
import com.example.meetup.payments.base.application.ISyncCommandHandler;
import com.example.meetup.payments.base.application.IAsyncCommandHandler;
import com.example.meetup.payments.base.application.IQuery;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PaymentsQueryDispatcher implements IQueryDispatcher {

    private final ApplicationContext applicationContext;

    @Override
    public <T> T executeQuery(IQuery query) {
        Class<? extends IQueryHandler> handlerType = query.getHandlerType();
        IQueryHandler handler = applicationContext.getBean(handlerType);
        return (T) handler.handle(query);
    }

}
