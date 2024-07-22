package com.example.meetup.administration.application.adminCreateMember;


import com.example.meetup.administration.base.application.IAsyncCommandHandler;
import com.example.meetup.administration.base.domain.IAggregateStore;



import com.example.meetup.administration.domain.members.MemberId;
import com.example.meetup.administration.domain.members.Member;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

    
@Component
@RequiredArgsConstructor
public class AdminCreateMemberCommandHandler implements IAsyncCommandHandler<AdminCreateMemberCommand> {

	private final IAggregateStore aggregateStore;


	@Override
	public void handle(AdminCreateMemberCommand command) {

   		var member = new Member(
   			new MemberId(command.getMemberId()),
   			command.getLogin(),
   			command.getEmail(),
   			command.getFirstName(),
   			command.getLastName(),
   			command.getRegisterDate());  	
		
		aggregateStore.save(member);

	}	
    	
}
