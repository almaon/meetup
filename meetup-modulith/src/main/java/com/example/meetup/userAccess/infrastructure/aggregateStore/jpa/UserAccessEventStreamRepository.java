package com.example.meetup.userAccess.infrastructure.aggregateStore.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccessEventStreamRepository extends JpaRepository<UserAccessEventStream, String>{

	UserAccessEventStream findByStreamId(String streamId);
}
