package com.example.meetup.meetings.infrastructure.aggregateStore.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingsEventStreamRepository extends JpaRepository<MeetingsEventStream, String>{

	MeetingsEventStream findByStreamId(String streamId);
}
