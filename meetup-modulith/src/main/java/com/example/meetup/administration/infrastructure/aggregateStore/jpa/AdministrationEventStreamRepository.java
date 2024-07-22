package com.example.meetup.administration.infrastructure.aggregateStore.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AdministrationEventStreamRepository extends JpaRepository<AdministrationEventStream, String>{

	AdministrationEventStream findByStreamId(String streamId);
}
