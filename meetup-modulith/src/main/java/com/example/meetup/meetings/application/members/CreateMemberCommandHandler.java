package com.example.meetup.meetings.application.members;


import com.example.meetup.meetings.base.application.IAsyncCommandHandler;
import com.example.meetup.meetings.base.domain.IAggregateStore;



import com.example.meetup.meetings.domain.member.MemberId;
import com.example.meetup.meetings.domain.member.Member;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

    
@Component
@RequiredArgsConstructor
public class CreateMemberCommandHandler implements IAsyncCommandHandler<CreateMemberCommand> {

	private final IAggregateStore aggregateStore;


	@Override
	public void handle(CreateMemberCommand command) {

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
