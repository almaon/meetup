package com.example.meetup.meetings.base.infrastructure;

import com.example.meetup.meetings.base.application.ICommand;

public interface ICommandDispatcher {

	<T> T executeCommandSync(ICommand command);
    
    void executeCommandAsync(ICommand command);
}
