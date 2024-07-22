package com.example.meetup.payments.application.subscriptions.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.example.meetup.payments.base.application.IQueryHandler;

import java.util.List;


@Component
@RequiredArgsConstructor
public class GetAllSubscriptionPaymentsQueryHandler implements IQueryHandler<GetAllSubscriptionPaymentsQuery, List<SubscriptionPaymentsView>> {

	private final SubscriptionPaymentsRepository subscriptionPaymentsRepository;
	
 	@Override
    public List<SubscriptionPaymentsView> handle(GetAllSubscriptionPaymentsQuery query) {
    	return subscriptionPaymentsRepository.findAll();
    }

}
