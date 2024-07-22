package com.example.meetup.administration.application.createAdmin;


import com.example.meetup.administration.base.application.IAsyncCommandHandler;
import com.example.meetup.administration.base.domain.IAggregateStore;



import com.example.meetup.administration.domain.administrator.AdministratorId;
import com.example.meetup.administration.domain.administrator.Administrator;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

    
@Component
@RequiredArgsConstructor
public class CreateAdminCommandHandler implements IAsyncCommandHandler<CreateAdminCommand> {

	private final IAggregateStore aggregateStore;


	@Override
	public void handle(CreateAdminCommand command) {

   		var administrator = new Administrator(
   			new AdministratorId(command.getAdminId()),
   			command.getLogin(),
   			command.getEmail(),
   			command.getFirstName(),
   			command.getLastName(),
   			command.getRegisterDate());  	
		
		aggregateStore.save(administrator);

	}	
    	
}
