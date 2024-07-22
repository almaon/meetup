package com.example.meetup.userAccess.application.users.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.example.meetup.userAccess.base.application.IQueryHandler;


@Component
@RequiredArgsConstructor
public class GetUsersByIdQueryHandler implements IQueryHandler<GetUsersByIdQuery, UsersView> {

	private final UsersRepository usersRepository;
	
 	@Override
    public UsersView handle(GetUsersByIdQuery query) {
    	return usersRepository.findByUserId(query.getUserId());
    }

}
