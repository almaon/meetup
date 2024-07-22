package com.example.meetup.payments.application.payers.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.example.meetup.payments.base.application.IQueryHandler;

import java.util.List;


@Component
@RequiredArgsConstructor
public class GetAllPayerByIdsQueryHandler implements IQueryHandler<GetAllPayerByIdsQuery, List<PayerView>> {

	private final PayerRepository payerRepository;
	
 	@Override
    public List<PayerView> handle(GetAllPayerByIdsQuery query) {
    	return payerRepository.findAllById(query.getIds());
    }

}
