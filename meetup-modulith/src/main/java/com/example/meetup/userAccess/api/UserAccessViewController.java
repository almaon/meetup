package com.example.meetup.userAccess.api;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.meetup.meetings.application.members.view.GetMemberByLoginQuery;
import com.example.meetup.meetings.application.members.view.MemberView;
import com.example.meetup.meetings.infrastructure.MeetingsQueryDispatcher;
import com.example.meetup.userAccess.application.confirmationLinks.view.ConfirmationLinksView;
import com.example.meetup.userAccess.application.confirmationLinks.view.GetAllConfirmationLinksByIdsQuery;
import com.example.meetup.userAccess.application.confirmationLinks.view.GetAllConfirmationLinksQuery;
import com.example.meetup.userAccess.application.confirmationLinks.view.GetConfirmationLinksByIdQuery;
import com.example.meetup.userAccess.application.users.view.GetAllUsersByIdsQuery;
import com.example.meetup.userAccess.application.users.view.GetAllUsersQuery;
import com.example.meetup.userAccess.application.users.view.GetUsersByIdQuery;
import com.example.meetup.userAccess.application.users.view.UsersView;
import com.example.meetup.userAccess.base.infrastructure.IQueryDispatcher;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/userAccess/views")
@RequiredArgsConstructor
@CrossOrigin("*")
public class UserAccessViewController {

	private final IQueryDispatcher queryDispatcher;
	
	private final MeetingsQueryDispatcher meetingsQueryDispatcher;
	
	
	@GetMapping("/confirmationLinks/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ConfirmationLinksView getConfirmationLinksById(@PathVariable String id) {

		return queryDispatcher.execute(new GetConfirmationLinksByIdQuery(id));
	}
	
	
	@GetMapping("/confirmationLinks")
	@ResponseStatus(HttpStatus.OK)
	public List<ConfirmationLinksView> getAllConfirmationLinksByIds(@RequestBody List<String> ids) {

		return queryDispatcher.execute(new GetAllConfirmationLinksByIdsQuery(ids));
	}
	
	
	@GetMapping("/all/confirmationLinks")
	@ResponseStatus(HttpStatus.OK)
	public List<ConfirmationLinksView> getAllConfirmationLinks() {

		return queryDispatcher.execute(new GetAllConfirmationLinksQuery());
	}	
	
	
	@GetMapping("/users/{id}")
	@ResponseStatus(HttpStatus.OK)
	public UsersView getUsersById(@PathVariable UUID id) {

		return queryDispatcher.execute(new GetUsersByIdQuery(id));
	}
	
	
	@GetMapping("/users")
	@ResponseStatus(HttpStatus.OK)
	public List<UsersView> getAllUsersByIds(@RequestBody List<UUID> ids) {

		return queryDispatcher.execute(new GetAllUsersByIdsQuery(ids));
	}
	
	
	@GetMapping("/all/users")
	@ResponseStatus(HttpStatus.OK)
	public List<UsersView> getAllUsers() {

		return queryDispatcher.execute(new GetAllUsersQuery());
	}

}
