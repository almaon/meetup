package com.example.meetup.userAccess.api;

import java.util.List;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.EventSubscription;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.meetup.userAccess.application.registerNewUser.RegisterNewUserCommand;
import com.example.meetup.userAccess.application.registerNewUser.RegisterNewUserCommandHandler.BusinessKeyHolder;
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
@RequestMapping("/public/api/userAccess/commands")
@RequiredArgsConstructor
@CrossOrigin("*")
@Slf4j(topic = "Controller")
public class UserAccessUnauthorizedCommandController {

	private final ICommandDispatcher commandDispatcher;

	@Operation(summary = "Confirm user registration with confirmation link")
	@ApiResponses(value = { 
	  @ApiResponse(responseCode = "404", description = "invalid confirmation link", 
	    content = @Content),
	  @ApiResponse(responseCode = "400", description = "invalid command or rule error", 
	    content = @Content) })
	@PostMapping("/confirmUserRegistration")
	@ResponseStatus(HttpStatus.OK)
	public void confirmUserRegistration() {//@RequestBody @Valid ConfirmUserRegistrationCommand command) {	
//		log(command);
//
//		commandDispatcher.executeAsync(command);
		
//		ProcessInstance pi = runtimeService.idreateProcessInstanceQuery().
//
//		EventSubscription subscription = runtimeService.createEventSubscriptionQuery()
//		  .processInstanceId(pi.getId()).eventType("message").singleResult();
//
//		runtimeService.messageEventReceived(subscription.getEventName(), subscription.getExecutionId());

		List<ProcessInstance>  processes = getAllRunningProcessInstances("userRegistration");
		ProcessInstance process = null;
		if (!processes.isEmpty())
		process = processes.get(0);
		
//		runtimeService.correlateMessage("isDone", "A");
		
		
		
		
		runtimeService.createMessageCorrelation("isDone")
	      .processInstanceBusinessKey(BusinessKeyHolder.businessKey)
	      .setVariable("gender", "female")
	      .correlate();

		
//		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
//x
//		RuntimeService runtimeService = processEngine.getRuntimeService()
//				processEngine.getProcessEngineConfiguration().

		
		System.out.println();
	}
	
	  public List<ProcessInstance> getAllRunningProcessInstances(String processDefinitionName) {
		    // get process engine and services
		    RuntimeService runtimeService = this.runtimeService;
		   
		    RepositoryService repositoryService = processEngine.getRepositoryService();

		    // query for latest process definition with given name
		    ProcessDefinition myProcessDefinition =
		        repositoryService.createProcessDefinitionQuery()
		           // .processDefinitionName(processDefinitionName)
		            .latestVersion()
		            .list().get(1);

		    // list all running/unsuspended instances of the process
		    List<ProcessInstance> processInstances =
		        runtimeService.createProcessInstanceQuery()
		            .processDefinitionId(myProcessDefinition.getId())
		            .active() // we only want the unsuspended process instances
		            .list();

		    return processInstances;
		  }
	private final RuntimeService runtimeService;
private final ProcessEngine processEngine;

	@Operation(summary = "Register new user and receive confirmation link")
	@ApiResponses(value = { 
	  @ApiResponse(responseCode = "400", description = "invalid command or rule error", 
	    content = @Content) })
	@PostMapping("/registerNewUser")
	@ResponseStatus(HttpStatus.CREATED)
	public String registerNewUser(@RequestBody @Valid RegisterNewUserCommand command) {
		log(command);

		return commandDispatcher.executeSync(command);
	}

	private void log(ICommand command) {
		log.info("Request command " + command.getClass().getSimpleName() + " id: " + command.getId() 
			+ "\t system time:" + SystemClock.now());
	}
}
