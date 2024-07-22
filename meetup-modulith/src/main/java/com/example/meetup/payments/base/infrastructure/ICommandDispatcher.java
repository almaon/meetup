package com.example.meetup.payments.base.infrastructure;

import com.example.meetup.payments.base.application.ICommand;

public interface ICommandDispatcher {

	<T> T executeCommandSync(ICommand command);
    
    void executeCommandAsync(ICommand command);
}
