package com.example.meetup.administration.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;


import com.example.meetup.administration.application.adminMeetingGroupProposals.view.AdminMeetingGroupProposalsView;
import com.example.meetup.administration.application.adminMeetingGroupProposals.view.GetAllAdminMeetingGroupProposalsQuery;
import com.example.meetup.administration.application.adminMeetingGroupProposals.view.GetAdminMeetingGroupProposalsByIdQuery;
import com.example.meetup.administration.application.adminMeetingGroupProposals.view.GetAllAdminMeetingGroupProposalsByIdsQuery;
import com.example.meetup.administration.application.administrators.view.AdministratorsView;
import com.example.meetup.administration.application.administrators.view.GetAllAdministratorsQuery;
import com.example.meetup.administration.application.administrators.view.GetAdministratorsByIdQuery;
import com.example.meetup.administration.application.administrators.view.GetAllAdministratorsByIdsQuery;

import java.util.UUID;
import java.util.List;

import lombok.RequiredArgsConstructor;

import com.example.meetup.administration.base.infrastructure.IQueryDispatcher;



@RestController
@RequestMapping("/api/administration/views")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AdministrationViewController {

	private final IQueryDispatcher queryDispatcher;

	
	
	@GetMapping("/MeetingGroupProposals/{id}")
	@ResponseStatus(HttpStatus.OK)
	public AdminMeetingGroupProposalsView getAdminMeetingGroupProposalsById(@PathVariable UUID id) {

		return queryDispatcher.executeQuery(new GetAdminMeetingGroupProposalsByIdQuery(id));
	}
	
	@GetMapping("/MeetingGroupProposals")
	@ResponseStatus(HttpStatus.OK)
	public List<AdminMeetingGroupProposalsView> getAllAdminMeetingGroupProposalsByIds(@RequestBody List<UUID> ids) {

		return queryDispatcher.executeQuery(new GetAllAdminMeetingGroupProposalsByIdsQuery(ids));
	}
	
	@GetMapping("/all/MeetingGroupProposals")
	@ResponseStatus(HttpStatus.OK)
	public List<AdminMeetingGroupProposalsView> getAllAdminMeetingGroupProposals() {

		return queryDispatcher.executeQuery(new GetAllAdminMeetingGroupProposalsQuery());
	}	
	
	
	@GetMapping("/administrators/{id}")
	@ResponseStatus(HttpStatus.OK)
	public AdministratorsView getAdministratorsById(@PathVariable UUID id) {

		return queryDispatcher.executeQuery(new GetAdministratorsByIdQuery(id));
	}
	
	@GetMapping("/administrators")
	@ResponseStatus(HttpStatus.OK)
	public List<AdministratorsView> getAllAdministratorsByIds(@RequestBody List<UUID> ids) {

		return queryDispatcher.executeQuery(new GetAllAdministratorsByIdsQuery(ids));
	}
	
	@GetMapping("/all/administrators")
	@ResponseStatus(HttpStatus.OK)
	public List<AdministratorsView> getAllAdministrators() {

		return queryDispatcher.executeQuery(new GetAllAdministratorsQuery());
	}	

}
