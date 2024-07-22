package com.example.meetup.administration.application.requestMeetingGroupProposalVerification;


import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.NoArgsConstructor;

import com.example.meetup.administration.base.application.CommandBase;
import com.example.meetup.administration.base.application.ICommandHandler;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestMeetingGroupProposalVerificationCommand extends CommandBase {

	private UUID meetingGroupProposalId;
	private String name;
	private String description;
	private String locationCity;
	private String locationCountryCode;
	private UUID proposalUserId;
	private LocalDateTime proposalDate;
    	
    @Override
    public Class<? extends ICommandHandler> getHandlerType() {
        return RequestMeetingGroupProposalVerificationCommandHandler.class;
    }

}
