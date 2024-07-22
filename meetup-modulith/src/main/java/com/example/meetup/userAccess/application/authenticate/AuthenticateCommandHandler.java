package com.example.meetup.userAccess.application.authenticate;


import com.example.meetup.userAccess.base.application.IAsyncCommandHandler;
import com.example.meetup.userAccess.base.domain.IAggregateStore;





import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

    
@Component
@RequiredArgsConstructor
public class AuthenticateCommandHandler implements IAsyncCommandHandler<AuthenticateCommand> {

	private final IAggregateStore aggregateStore;


	@Override
	public void handle(AuthenticateCommand command) {

		/* implement:
    	
    	
		
		aggregateStore.save();
		*/

	}	
    	
}
