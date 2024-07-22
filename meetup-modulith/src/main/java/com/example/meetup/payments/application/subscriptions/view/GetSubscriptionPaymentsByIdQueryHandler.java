package com.example.meetup.payments.application.subscriptions.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.example.meetup.payments.base.application.IQueryHandler;


@Component
@RequiredArgsConstructor
public class GetSubscriptionPaymentsByIdQueryHandler implements IQueryHandler<GetSubscriptionPaymentsByIdQuery, SubscriptionPaymentsView> {

	private final SubscriptionPaymentsRepository subscriptionPaymentsRepository;
	
 	@Override
    public SubscriptionPaymentsView handle(GetSubscriptionPaymentsByIdQuery query) {
    	return subscriptionPaymentsRepository.findByPaymentId(query.getPaymentId());
    }

}
