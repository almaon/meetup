package com.example.meetup.meetings.application.meetingGroupProposals;


import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.NoArgsConstructor;

import com.example.meetup.meetings.base.application.CommandBase;
import com.example.meetup.meetings.base.application.ICommandHandler;

import jakarta.validation.constraints.NotBlank;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProposeMeetingGroupCommand extends CommandBase {

	@NotBlank
	private String name;
	@NotBlank
	private String description;
	@NotBlank
	private String locationCity;
	@NotBlank
	private String locationCountryCode;
    	
    @Override
    public Class<? extends ICommandHandler> getHandlerType() {
        return ProposeMeetingGroupCommandHandler.class;
    }

}
