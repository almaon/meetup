package com.example.meetup.userAccess.application.confirmationLinks.view;

import java.util.List;
import java.util.UUID;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface ConfirmationLinksRepository extends JpaRepository<ConfirmationLinksView, String> {

	ConfirmationLinksView findByConfirmationLink(String confirmationLink);
	

	ConfirmationLinksView findByUserRegistrationId(UUID userRegistrationId);
	ConfirmationLinksView findByLogin(String login);


}
