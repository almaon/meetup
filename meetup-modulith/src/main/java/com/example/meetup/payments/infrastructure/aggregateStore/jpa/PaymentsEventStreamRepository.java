package com.example.meetup.payments.infrastructure.aggregateStore.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentsEventStreamRepository extends JpaRepository<PaymentsEventStream, String>{

	PaymentsEventStream findByStreamId(String streamId);
}
