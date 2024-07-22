package com.example.meetup.payments.application.payers.view;

import java.util.List;

import java.util.UUID;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface PayerRepository extends JpaRepository<PayerView, UUID> {

	PayerView findByPayerId(UUID payerId);
	


}
