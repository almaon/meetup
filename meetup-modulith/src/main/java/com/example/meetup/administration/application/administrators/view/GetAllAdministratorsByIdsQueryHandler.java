package com.example.meetup.administration.application.administrators.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.example.meetup.administration.base.application.IQueryHandler;

import java.util.List;


@Component
@RequiredArgsConstructor
public class GetAllAdministratorsByIdsQueryHandler implements IQueryHandler<GetAllAdministratorsByIdsQuery, List<AdministratorsView>> {

	private final AdministratorsRepository administratorsRepository;
	
 	@Override
    public List<AdministratorsView> handle(GetAllAdministratorsByIdsQuery query) {
    	return administratorsRepository.findAllById(query.getIds());
    }

}
