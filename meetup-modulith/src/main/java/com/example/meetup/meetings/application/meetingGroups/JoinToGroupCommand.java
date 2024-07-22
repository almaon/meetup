package com.example.meetup.meetings.application.meetingGroups;

import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.NoArgsConstructor;

import com.example.meetup.meetings.base.application.CommandBase;
import com.example.meetup.meetings.base.application.ICommandHandler;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JoinToGroupCommand extends CommandBase {

	@NotNull
	private UUID meetingGroupId;

	@Override
	public Class<? extends ICommandHandler> getHandlerType() {
		return JoinToGroupCommandHandler.class;
	}

}
