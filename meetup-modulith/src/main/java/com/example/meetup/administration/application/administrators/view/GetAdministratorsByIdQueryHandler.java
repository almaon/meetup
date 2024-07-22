package com.example.meetup.administration.application.administrators.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.example.meetup.administration.base.application.IQueryHandler;


@Component
@RequiredArgsConstructor
public class GetAdministratorsByIdQueryHandler implements IQueryHandler<GetAdministratorsByIdQuery, AdministratorsView> {

	private final AdministratorsRepository administratorsRepository;
	
 	@Override
    public AdministratorsView handle(GetAdministratorsByIdQuery query) {
    	return administratorsRepository.findByAdministratorId(query.getAdministratorId());
    }

}
