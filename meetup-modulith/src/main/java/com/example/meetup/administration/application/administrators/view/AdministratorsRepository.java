package com.example.meetup.administration.application.administrators.view;

import java.util.List;

import java.util.UUID;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface AdministratorsRepository extends JpaRepository<AdministratorsView, UUID> {

	AdministratorsView findByAdministratorId(UUID administratorId);
	

	AdministratorsView findByLogin(String login);

}
