package com.example.meetup.userAccess.application.addAdminUser;


import com.example.meetup.userAccess.base.application.IAsyncCommandHandler;
import com.example.meetup.userAccess.base.domain.IAggregateStore;





import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

    
@Component
@RequiredArgsConstructor
public class AddAdminUserCommandHandler implements IAsyncCommandHandler<AddAdminUserCommand> {

	private final IAggregateStore aggregateStore;


	@Override
	public void handle(AddAdminUserCommand command) {

		/* implement:
    	
    	
		
		aggregateStore.save();
		*/

	}	
    	
}
