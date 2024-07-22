package com.example.meetup.payments.application.subscriptions.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.example.meetup.payments.base.application.IQueryHandler;


@Component
@RequiredArgsConstructor
public class GetSubscriptionsByIdQueryHandler implements IQueryHandler<GetSubscriptionsByIdQuery, SubscriptionsView> {

	private final SubscriptionsRepository subscriptionsRepository;
	
 	@Override
    public SubscriptionsView handle(GetSubscriptionsByIdQuery query) {
    	return subscriptionsRepository.findBySubscriptionId(query.getSubscriptionId());
    }

}
