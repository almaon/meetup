package com.example.meetup.userAccess.application.users.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.example.meetup.userAccess.base.application.IQueryHandler;

import java.util.List;


@Component
@RequiredArgsConstructor
public class GetAllUsersByIdsQueryHandler implements IQueryHandler<GetAllUsersByIdsQuery, List<UsersView>> {

	private final UsersRepository usersRepository;
	
 	@Override
    public List<UsersView> handle(GetAllUsersByIdsQuery query) {
    	return usersRepository.findAllById(query.getIds());
    }

}
