package com.example.meetup.administration.application.adminRejectMeetingGroupProposal;

import java.util.UUID;

import com.example.meetup.administration.base.application.CommandBase;
import com.example.meetup.administration.base.application.ICommandHandler;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminRejectMeetingGroupProposalCommand extends CommandBase {

	@NotNull
	private UUID meetingGroupProposalId;

	@NotBlank
	private String decisionRejectReason;

	@Override
	public Class<? extends ICommandHandler> getHandlerType() {
		return AdminRejectMeetingGroupProposalCommandHandler.class;
	}

}
