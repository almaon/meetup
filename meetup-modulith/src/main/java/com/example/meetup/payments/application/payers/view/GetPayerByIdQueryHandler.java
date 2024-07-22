package com.example.meetup.payments.application.payers.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.example.meetup.payments.base.application.IQueryHandler;


@Component
@RequiredArgsConstructor
public class GetPayerByIdQueryHandler implements IQueryHandler<GetPayerByIdQuery, PayerView> {

	private final PayerRepository payerRepository;
	
 	@Override
    public PayerView handle(GetPayerByIdQuery query) {
    	return payerRepository.findByPayerId(query.getPayerId());
    }

}
