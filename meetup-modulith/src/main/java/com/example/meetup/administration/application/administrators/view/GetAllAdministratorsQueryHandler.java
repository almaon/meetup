package com.example.meetup.administration.application.administrators.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.example.meetup.administration.base.application.IQueryHandler;

import java.util.List;


@Component
@RequiredArgsConstructor
public class GetAllAdministratorsQueryHandler implements IQueryHandler<GetAllAdministratorsQuery, List<AdministratorsView>> {

	private final AdministratorsRepository administratorsRepository;
	
 	@Override
    public List<AdministratorsView> handle(GetAllAdministratorsQuery query) {
    	return administratorsRepository.findAll();
    }

}
