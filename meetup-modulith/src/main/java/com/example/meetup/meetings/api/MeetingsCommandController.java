package com.example.meetup.meetings.api;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.meetup.meetings.application.meetingCommentingConfigurations.DisableMeetingCommentingConfigurationCommand;
import com.example.meetup.meetings.application.meetingCommentingConfigurations.EnableMeetingCommentingConfigurationCommand;
import com.example.meetup.meetings.application.meetingComments.AddMeetingCommentCommand;
import com.example.meetup.meetings.application.meetingComments.AddMeetingCommentLikeCommand;
import com.example.meetup.meetings.application.meetingComments.AddReplyToMeetingCommentCommand;
import com.example.meetup.meetings.application.meetingComments.EditMeetingCommentCommand;
import com.example.meetup.meetings.application.meetingComments.RemoveMeetingCommentCommand;
import com.example.meetup.meetings.application.meetingComments.RemoveMeetingCommentLikeCommand;
import com.example.meetup.meetings.application.meetingGroupProposals.ProposeMeetingGroupCommand;
import com.example.meetup.meetings.application.meetingGroups.EditMeetingGroupGeneralAttributesCommand;
import com.example.meetup.meetings.application.meetingGroups.JoinToGroupCommand;
import com.example.meetup.meetings.application.meetingGroups.LeaveMeetingGroupCommand;
import com.example.meetup.meetings.application.meetings.AddMeetingAttendeeCommand;
import com.example.meetup.meetings.application.meetings.AddMeetingNotAttendeeCommand;
import com.example.meetup.meetings.application.meetings.CancelMeetingCommand;
import com.example.meetup.meetings.application.meetings.ChangeMeetingMainAttributesCommand;
import com.example.meetup.meetings.application.meetings.CreateMeetingCommand;
import com.example.meetup.meetings.application.meetings.RemoveMeetingAttendeeCommand;
import com.example.meetup.meetings.application.meetings.SetMeetingAttendeeRoleCommand;
import com.example.meetup.meetings.application.meetings.SetMeetingHostRoleCommand;
import com.example.meetup.meetings.application.meetings.SignOffMemberFromWaitlistCommand;
import com.example.meetup.meetings.application.meetings.SignUpMemberToWaitlistCommand;
import com.example.meetup.meetings.base.SystemClock;
import com.example.meetup.meetings.base.application.ICommand;
import com.example.meetup.meetings.base.infrastructure.ICommandDispatcher;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/api/meetings/commands")
@RequiredArgsConstructor
@CrossOrigin("*")
@Slf4j(topic = "Controller")
public class MeetingsCommandController {

	private final ICommandDispatcher commandDispatcher;


	@Operation(summary = "Create meeting group proposal. A meeting group must be approved by an administrator.")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "400", description = "invalid command or rule error", 
					content = @Content) })
	@PostMapping("/proposeMeetingGroup")
	@ResponseStatus(HttpStatus.OK)
	public UUID proposeMeetingGroup(@RequestBody @Valid ProposeMeetingGroupCommand command) {
		log(command);

		return commandDispatcher.executeCommandSync(command);
	}


	@PostMapping("/joinToGroup")
	@ResponseStatus(HttpStatus.OK)
	public void joinToGroup(@RequestBody @Valid JoinToGroupCommand command) {
		log(command);

		commandDispatcher.executeCommandAsync(command);
	}

	@Operation(summary = "Create meeting. User must be member of meeting group.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "400", description = "invalid command or rule error", 
					content = @Content) })
	@PostMapping("/createMeeting")
	@ResponseStatus(HttpStatus.OK)
	public UUID createMeeting(@RequestBody @Valid CreateMeetingCommand command) {
		log(command);

		return commandDispatcher.executeCommandSync(command);
	}

	@Operation(summary = "Set user as meeting attendee. User must be member of meeting group.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "400", description = "invalid command or rule error", 
					content = @Content) })
	@PostMapping("/addMeetingAttendee")
	@ResponseStatus(HttpStatus.OK)
	public void addMeetingAttendee(@RequestBody @Valid AddMeetingAttendeeCommand command) {
		log(command);

		commandDispatcher.executeCommandAsync(command);
	}

	@Operation(summary = "Enable meeting comments. User must be host of meeting.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "400", description = "invalid command or rule error", 
					content = @Content) })
	@PostMapping("/enableMeetingCommentingConfiguration")
	@ResponseStatus(HttpStatus.OK)
	public void enableMeetingCommentingConfiguration(@RequestBody @Valid EnableMeetingCommentingConfigurationCommand command) {
		log(command);

		commandDispatcher.executeCommandAsync(command);
	}

	@Operation(summary = "Add meeting comment. User must be attendee of meeting.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "400", description = "invalid command or rule error", 
					content = @Content) })
	@PostMapping("/addMeetingComment")
	@ResponseStatus(HttpStatus.OK)
	public UUID addMeetingComment(@RequestBody @Valid AddMeetingCommentCommand command) {
		log(command);

		return commandDispatcher.executeCommandSync(command);
	}


	@PostMapping("/addMeetingCommentLike")
	@ResponseStatus(HttpStatus.OK)
	public void addMeetingCommentLike(@RequestBody @Valid AddMeetingCommentLikeCommand command) {
		log(command);

		commandDispatcher.executeCommandAsync(command);
	}


	@PostMapping("/addReplyToMeetingComment")
	@ResponseStatus(HttpStatus.OK)
	public UUID addReplyToMeetingComment(@RequestBody @Valid AddReplyToMeetingCommentCommand command) {
		log(command);

		return commandDispatcher.executeCommandSync(command);
	}

	@Operation(summary = "Assign attendee host role. User must be host of meeting.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "400", description = "invalid command or rule error", 
					content = @Content) })
	@PostMapping("/setMeetingHostRole")
	@ResponseStatus(HttpStatus.OK)
	public void setMeetingHostRole(@RequestBody @Valid SetMeetingHostRoleCommand command) {
		log(command);

		commandDispatcher.executeCommandAsync(command);
	}


	@PostMapping("/signUpMemberToWaitlist")
	@ResponseStatus(HttpStatus.OK)
	public void signUpMemberToWaitlist(@RequestBody @Valid SignUpMemberToWaitlistCommand command) {
		log(command);

		commandDispatcher.executeCommandAsync(command);
	}


	@PostMapping("/editMeetingComment")
	@ResponseStatus(HttpStatus.OK)
	public void editMeetingComment(@RequestBody @Valid EditMeetingCommentCommand command) {
		log(command);

		commandDispatcher.executeCommandAsync(command);
	}


	@PostMapping("/removeMeetingCommentLike")
	@ResponseStatus(HttpStatus.OK)
	public void removeMeetingCommentLike(@RequestBody @Valid RemoveMeetingCommentLikeCommand command) {
		log(command);

		commandDispatcher.executeCommandAsync(command);
	}


	@PostMapping("/editMeetingGroupGeneralAttributes")
	@ResponseStatus(HttpStatus.OK)
	public void editMeetingGroupGeneralAttributes(@RequestBody @Valid EditMeetingGroupGeneralAttributesCommand command) {
		log(command);

		commandDispatcher.executeCommandAsync(command);
	}


	@PostMapping("/leaveMeetingGroup")
	@ResponseStatus(HttpStatus.OK)
	public void leaveMeetingGroup(@RequestBody @Valid LeaveMeetingGroupCommand command) {
		log(command);

		commandDispatcher.executeCommandAsync(command);
	}


	@PostMapping("/removeMeetingComment")
	@ResponseStatus(HttpStatus.OK)
	public void removeMeetingComment(@RequestBody @Valid RemoveMeetingCommentCommand command) {
		log(command);

		commandDispatcher.executeCommandAsync(command);
	}


	@PostMapping("/addMeetingNotAttendee")
	@ResponseStatus(HttpStatus.OK)
	public void addMeetingNotAttendee(@RequestBody @Valid AddMeetingNotAttendeeCommand command) {
		log(command);

		commandDispatcher.executeCommandAsync(command);
	}


	@PostMapping("/changeMeetingMainAttributes")
	@ResponseStatus(HttpStatus.OK)
	public void changeMeetingMainAttributes(@RequestBody @Valid ChangeMeetingMainAttributesCommand command) {
		log(command);

		commandDispatcher.executeCommandAsync(command);
	}


	@PostMapping("/cancelMeeting")
	@ResponseStatus(HttpStatus.OK)
	public void cancelMeeting(@RequestBody @Valid CancelMeetingCommand command) {
		log(command);

		commandDispatcher.executeCommandAsync(command);
	}


	@PostMapping("/removeMeetingAttendee")
	@ResponseStatus(HttpStatus.OK)
	public void removeMeetingAttendee(@RequestBody @Valid RemoveMeetingAttendeeCommand command) {
		log(command);

		commandDispatcher.executeCommandAsync(command);
	}


	@PostMapping("/setMeetingAttendeeRole")
	@ResponseStatus(HttpStatus.OK)
	public void setMeetingAttendeeRole(@RequestBody @Valid SetMeetingAttendeeRoleCommand command) {
		log(command);

		commandDispatcher.executeCommandAsync(command);
	}


	@PostMapping("/signOffMemberFromWaitlist")
	@ResponseStatus(HttpStatus.OK)
	public void signOffMemberFromWaitlist(@RequestBody @Valid SignOffMemberFromWaitlistCommand command) {
		log(command);

		commandDispatcher.executeCommandAsync(command);
	}


	@PostMapping("/disableMeetingCommentingConfiguration")
	@ResponseStatus(HttpStatus.OK)
	public void disableMeetingCommentingConfiguration(@RequestBody @Valid DisableMeetingCommentingConfigurationCommand command) {
		log(command);

		commandDispatcher.executeCommandAsync(command);
	}

	private void log(ICommand command) {
		log.info("Request command " + command.getClass().getSimpleName() + " id: " + command.getId() 
		+ "\t system time:" + SystemClock.now());
	}

}
