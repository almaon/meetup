package com.example.meetup.administration.application.administrators.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.example.meetup.administration.base.application.IQueryHandler;


@Component
@RequiredArgsConstructor
public class GetAdministratorsByLoginQueryHandler implements IQueryHandler<GetAdministratorsByLoginQuery, AdministratorsView> {

	private final AdministratorsRepository administratorsRepository;
	
 	@Override
    public AdministratorsView handle(GetAdministratorsByLoginQuery query) {
    	return administratorsRepository.findByLogin(query.getLogin());
    }

}
