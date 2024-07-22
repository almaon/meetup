package com.example.meetup.userAccess.application.confirmationLinks.view;


import jakarta.persistence.Column;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.UUID;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ConfirmationLinksView {
	
	@Id
	@Column(name = "id", length = 16, unique = true, nullable = false)
	private String confirmationLink;

	private String login;
	private UUID userRegistrationId;
	
}
