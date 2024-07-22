package com.example.meetup.payments.application.payers.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.example.meetup.payments.base.application.IQueryHandler;

import java.util.List;


@Component
@RequiredArgsConstructor
public class GetAllPayerQueryHandler implements IQueryHandler<GetAllPayerQuery, List<PayerView>> {

	private final PayerRepository payerRepository;
	
 	@Override
    public List<PayerView> handle(GetAllPayerQuery query) {
    	return payerRepository.findAll();
    }

}
