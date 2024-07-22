package com.example.meetup.payments.application.subscriptions.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.example.meetup.payments.base.application.IQueryHandler;

import java.util.List;


@Component
@RequiredArgsConstructor
public class GetAllSubscriptionsQueryHandler implements IQueryHandler<GetAllSubscriptionsQuery, List<SubscriptionsView>> {

	private final SubscriptionsRepository subscriptionsRepository;
	
 	@Override
    public List<SubscriptionsView> handle(GetAllSubscriptionsQuery query) {
    	return subscriptionsRepository.findAll();
    }

}
