package com.example.meetup.administration.base.infrastructure;

import com.example.meetup.administration.base.application.ICommand;

public interface ICommandDispatcher {

	<T> T executeCommandSync(ICommand command);
    
    void executeCommandAsync(ICommand command);
}
