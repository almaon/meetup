package com.example.meetup.userAccess.base.infrastructure;

import com.example.meetup.userAccess.base.application.ICommand;

public interface ICommandDispatcher {

	<T> T executeSync(ICommand command);
    
    void executeAsync(ICommand command);
}
