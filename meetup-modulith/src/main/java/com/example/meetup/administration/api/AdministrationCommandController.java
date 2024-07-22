package com.example.meetup.administration.api;

import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.meetup.administration.application.adminAcceptMeetingGroupProposal.AdminAcceptMeetingGroupProposalCommand;
import com.example.meetup.administration.application.adminRejectMeetingGroupProposal.AdminRejectMeetingGroupProposalCommand;
import com.example.meetup.administration.application.requestMeetingGroupProposalVerification.RequestMeetingGroupProposalVerificationCommand;
import com.example.meetup.administration.base.infrastructure.ICommandDispatcher;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;



@RestController
@Transactional
@RequestMapping("/api/administration/commands")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AdministrationCommandController {

	private final ICommandDispatcher commandDispatcher;
	

	@Operation(summary = "Administrator Accept Meeting Group Proposal")
	@ApiResponses(value = { 
	  @ApiResponse(responseCode = "404", description = "Meeting Group Proposal not found", 
	    content = @Content),
	  @ApiResponse(responseCode = "400", description = "No Meeting Group Proposal id provided", 
	    content = @Content) })
	@PostMapping("/AcceptMeetingGroupProposal")
	@ResponseStatus(HttpStatus.OK)
	public void adminAcceptMeetingGroupProposal(@RequestBody @Valid AdminAcceptMeetingGroupProposalCommand adminAcceptMeetingGroupProposalCommand) {

		commandDispatcher.executeCommandAsync(adminAcceptMeetingGroupProposalCommand);
	}
	
	@Operation(summary = "Administrator Reject Meeting Group Proposal")
	@ApiResponses(value = { 
	  @ApiResponse(responseCode = "404", description = "Meeting Group Proposal not found", 
	    content = @Content),
	  @ApiResponse(responseCode = "400", description = "No Meeting Group Proposal id provided", 
	    content = @Content) })
	@PostMapping("/RejectMeetingGroupProposal")
	@ResponseStatus(HttpStatus.OK)
	public void adminRejectMeetingGroupProposal(@RequestBody @Valid AdminRejectMeetingGroupProposalCommand adminRejectMeetingGroupProposalCommand) {

		commandDispatcher.executeCommandAsync(adminRejectMeetingGroupProposalCommand);
	}

	@PostMapping("/create")
	@ResponseStatus(HttpStatus.OK)
	public void create(@RequestBody @Valid RequestMeetingGroupProposalVerificationCommand command) {

		commandDispatcher.executeCommandAsync(command);
	}
	
}
