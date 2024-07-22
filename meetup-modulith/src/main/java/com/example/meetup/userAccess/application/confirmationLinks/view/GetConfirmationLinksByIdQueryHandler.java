package com.example.meetup.userAccess.application.confirmationLinks.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.example.meetup.userAccess.base.application.IQueryHandler;


@Component
@RequiredArgsConstructor
public class GetConfirmationLinksByIdQueryHandler implements IQueryHandler<GetConfirmationLinksByIdQuery, ConfirmationLinksView> {

	private final ConfirmationLinksRepository confirmationLinksRepository;
	
 	@Override
    public ConfirmationLinksView handle(GetConfirmationLinksByIdQuery query) {
    	return confirmationLinksRepository.findByConfirmationLink(query.getConfirmationLink());
    }

}
