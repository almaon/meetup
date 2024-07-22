package com.example.meetup.payments.application.subscriptions.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.example.meetup.payments.base.application.IQueryHandler;

import java.util.List;


@Component
@RequiredArgsConstructor
public class GetAllSubscriptionsByIdsQueryHandler implements IQueryHandler<GetAllSubscriptionsByIdsQuery, List<SubscriptionsView>> {

	private final SubscriptionsRepository subscriptionsRepository;
	
 	@Override
    public List<SubscriptionsView> handle(GetAllSubscriptionsByIdsQuery query) {
    	return subscriptionsRepository.findAllById(query.getIds());
    }

}
