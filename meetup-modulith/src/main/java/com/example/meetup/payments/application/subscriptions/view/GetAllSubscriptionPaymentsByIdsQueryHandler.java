package com.example.meetup.payments.application.subscriptions.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.example.meetup.payments.base.application.IQueryHandler;

import java.util.List;


@Component
@RequiredArgsConstructor
public class GetAllSubscriptionPaymentsByIdsQueryHandler implements IQueryHandler<GetAllSubscriptionPaymentsByIdsQuery, List<SubscriptionPaymentsView>> {

	private final SubscriptionPaymentsRepository subscriptionPaymentsRepository;
	
 	@Override
    public List<SubscriptionPaymentsView> handle(GetAllSubscriptionPaymentsByIdsQuery query) {
    	return subscriptionPaymentsRepository.findAllById(query.getIds());
    }

}
