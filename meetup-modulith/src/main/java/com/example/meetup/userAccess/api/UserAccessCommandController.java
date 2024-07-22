package com.example.meetup.userAccess.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.meetup.userAccess.application.createAdmin.UserAccessCreateAdminCommand;
import com.example.meetup.userAccess.base.SystemClock;
import com.example.meetup.userAccess.base.application.ICommand;
import com.example.meetup.userAccess.base.infrastructure.ICommandDispatcher;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/userAccess/commands")
@RequiredArgsConstructor
@CrossOrigin("*")
@Slf4j(topic = "Controller")
public class UserAccessCommandController {

	private final ICommandDispatcher commandDispatcher;

	@Operation(summary = "Register admin")
	@ApiResponses(value = { 
	  @ApiResponse(responseCode = "400", description = "invalid command or rule error", 
	    content = @Content) })
	@PostMapping("/createAdmin")
	@ResponseStatus(HttpStatus.CREATED)
	public void createAdmin(@RequestBody @Valid UserAccessCreateAdminCommand command) {
		log(command);
		
		commandDispatcher.executeAsync(command);
	}
	
	private void log(ICommand command) {
		log.info("Request command " + command.getClass().getSimpleName() + " id: " + command.getId() 
			+ "\t system time:" + SystemClock.now());
	}
}
