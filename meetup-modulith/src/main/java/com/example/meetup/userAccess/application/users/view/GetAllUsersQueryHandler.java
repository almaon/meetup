package com.example.meetup.userAccess.application.users.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.example.meetup.userAccess.base.application.IQueryHandler;

import java.util.List;


@Component
@RequiredArgsConstructor
public class GetAllUsersQueryHandler implements IQueryHandler<GetAllUsersQuery, List<UsersView>> {

	private final UsersRepository usersRepository;
	
 	@Override
    public List<UsersView> handle(GetAllUsersQuery query) {
    	return usersRepository.findAll();
    }

}
