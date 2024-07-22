package com.example.meetup.meetings.application.meetingGroups;

import java.util.UUID;

import com.example.meetup.meetings.base.application.CommandBase;
import com.example.meetup.meetings.base.application.ICommandHandler;

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
public class EditMeetingGroupGeneralAttributesCommand extends CommandBase {

	@NotNull
	private UUID meetingGroupId;
	@NotBlank
	private String locationCountryCode;
	@NotBlank
	private String name;
	@NotBlank
	private String description;
	@NotBlank
	private String locationCity;

	@Override
	public Class<? extends ICommandHandler> getHandlerType() {
		return EditMeetingGroupGeneralAttributesCommandHandler.class;
	}

}
