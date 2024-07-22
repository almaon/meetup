package com.example.meetup.userAccess.application.confirmationLinks.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.example.meetup.userAccess.base.application.IQueryHandler;


@Component
@RequiredArgsConstructor
public class GetConfirmationLinksByLoginQueryHandler implements IQueryHandler<GetConfirmationLinksByLoginQuery, ConfirmationLinksView> {

	private final ConfirmationLinksRepository confirmationLinksRepository;
	
 	@Override
    public ConfirmationLinksView handle(GetConfirmationLinksByLoginQuery query) {
    	return confirmationLinksRepository.findByLogin(query.getLogin());
    }

}
