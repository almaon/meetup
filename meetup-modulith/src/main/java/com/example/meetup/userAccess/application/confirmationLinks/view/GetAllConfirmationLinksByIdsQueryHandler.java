package com.example.meetup.userAccess.application.confirmationLinks.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.example.meetup.userAccess.base.application.IQueryHandler;

import java.util.List;


@Component
@RequiredArgsConstructor
public class GetAllConfirmationLinksByIdsQueryHandler implements IQueryHandler<GetAllConfirmationLinksByIdsQuery, List<ConfirmationLinksView>> {

	private final ConfirmationLinksRepository confirmationLinksRepository;
	
 	@Override
    public List<ConfirmationLinksView> handle(GetAllConfirmationLinksByIdsQuery query) {
    	return confirmationLinksRepository.findAllById(query.getIds());
    }

}
