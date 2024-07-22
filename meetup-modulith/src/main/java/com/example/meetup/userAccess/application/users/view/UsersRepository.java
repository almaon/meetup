package com.example.meetup.userAccess.application.users.view;

import java.util.List;

import java.util.UUID;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface UsersRepository extends JpaRepository<UsersView, UUID> {

	UsersView findByUserId(UUID userId);
	


}
